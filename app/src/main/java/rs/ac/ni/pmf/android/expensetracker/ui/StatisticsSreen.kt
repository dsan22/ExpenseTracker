package rs.ac.ni.pmf.android.expensetracker.ui

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import rs.ac.ni.pmf.android.expensetracker.R
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.StatisticsViewModel
import kotlin.math.abs


class MonthAxisValueFormatter(context: Context) : com.github.mikephil.charting.formatter.ValueFormatter() {
     private val monthLabels: List<String> = listOf(
         context.getString( R.string.january),
         context.getString( R.string.february),
         context.getString( R.string.march),
         context.getString( R.string.april),
         context.getString( R.string.may),
         context.getString( R.string.jun),
         context.getString( R.string.july),
         context.getString( R.string.august),
         context.getString( R.string.september),
         context.getString( R.string.october),
         context.getString( R.string.november),
         context.getString( R.string.december),

         )
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index >= 0 && index < monthLabels.size) {
            monthLabels[index]
        } else {
            ""
        }
    }
}

@Composable
fun LineGraph(
    xData: List<Int>,
    yData: List<Double>,
    dataLabel: String,
    modifier: Modifier = Modifier
){
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            val chart = BarChart(context)  // Initialise the chart

            val colors = yData.map { if (it < 0) Color.RED else Color.CYAN }
            val absYData=yData.map {  abs(it) }
            val entries: List<BarEntry> = xData.zip(absYData) { x, y -> BarEntry(x.toFloat(), y.toFloat()) }
            val dataSet = BarDataSet(entries, dataLabel).apply {

                this.valueTextSize=15f
                this.colors = colors

            }
            chart.data = BarData(dataSet)  // Pass the dataset to the chart

            //Styling
            chart.legend.isEnabled=false
            chart.description.isEnabled=false
            chart.axisRight.isEnabled=false
            chart.axisLeft.textSize=15f
            chart.xAxis.position=XAxis.XAxisPosition.BOTTOM
            chart.xAxis.textSize=15f
            chart.xAxis.granularity=1f
            chart.xAxis.valueFormatter=MonthAxisValueFormatter(context)

            // Refresh and return the chart
            chart.invalidate()
            chart
        }
    )
}

@Composable
fun StatisticsScreen(
    onStatisticsCLick:()->Unit,
    onIncomeClick:()->Unit,
    onExpenseClick:()->Unit,
    viewModel:StatisticsViewModel= viewModel(factory = AppViewModelProvider.Factory) )
{
    val incomeUiState by viewModel.incomeUiState.collectAsState()
    val incomeData=incomeUiState.incomeByMonth
    val xIncomeData=incomeData.keys.toList()
    val yIncomeData=incomeData.values.toList()

    val expenseUiState by viewModel.expenseUiState.collectAsState()
    val expenseData=expenseUiState.expensesByMonth
    val xExpenseData=expenseData.keys.toList()
    val yExpenseData=expenseData.values.toList()

    var difCalculated by remember { mutableStateOf(false) }
    val difMap = mutableMapOf<Int, Double>()

    for ((key, value) in incomeData) {
        difMap[key] = value
    }

    for ((key, value) in expenseData) {
        if (difMap.containsKey(key)) {
            difMap[key] = difMap[key]!! - value
        } else {
            difMap[key] = -value
        }
    }
    val xDiffData=difMap.keys.toList()
    val yDiffData=difMap.values.toList()
    difCalculated=true




    Scaffold(
        bottomBar = {
            BottomBar(
                onStatisticsCLick = onStatisticsCLick,
                onIncomeClick = onIncomeClick,
                onExpenseClick = onExpenseClick
            )
        }
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(it)
        )  {
            if(xIncomeData.isNotEmpty()&&yIncomeData.isNotEmpty()){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 23.sp,
                    text = stringResource( R.string.income))
                LineGraph(xData =xIncomeData, yData = yIncomeData , dataLabel ="", modifier = Modifier
                    .padding(8.dp)
                    .height(300.dp) )
            }
            if(xExpenseData.isNotEmpty()&&yExpenseData.isNotEmpty()){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 23.sp,
                    text = stringResource( R.string.expense))
                LineGraph(xData =xExpenseData, yData = yExpenseData , dataLabel ="", modifier = Modifier
                    .padding(8.dp)
                    .height(300.dp) )
            }
            if(xDiffData.isNotEmpty()&&yDiffData.isNotEmpty()&&difCalculated){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 23.sp,
                    text = stringResource( R.string.diff_in_income_and_expense))
                LineGraph(xData =xDiffData, yData = yDiffData , dataLabel ="", modifier = Modifier
                    .padding(8.dp)
                    .height(300.dp) )
            }
        }

    }



}

@Preview(showBackground = true)
@Composable
fun GraphPreview(){
    val xData= listOf(1,2,3,4)
    val yData= listOf(5.0,2.0,3.0,4.0 )
    LineGraph(xData =xData , yData =yData , dataLabel ="Test" )
}