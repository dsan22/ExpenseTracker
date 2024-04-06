package rs.ac.ni.pmf.android.expensetracker.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.StatisticsViewModel


class MonthAxisValueFormatter() : com.github.mikephil.charting.formatter.ValueFormatter() {
     private val monthLabels: List<String> = listOf(
         "January",
         "February",
         "March",
         "April",
         "May",
         "Jun",
         "July",
         "August",
         "September",
         "October",
         "November",
         "December",
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
            val entries: List<BarEntry> = xData.zip(yData) { x, y -> BarEntry(x.toFloat(), y.toFloat()) }  // Convert the x and y data into entries
            val dataSet = BarDataSet(entries, dataLabel).apply {
                //setDrawValues(false)
                this.valueTextSize=13f
            }  // Create a dataset of entries
            chart.data = BarData(dataSet)  // Pass the dataset to the chart

            //Styling
            chart.legend.isEnabled=false
            chart.description.isEnabled=false
            chart.axisRight.isEnabled=false
            chart.axisLeft.textSize=15f
            chart.xAxis.position=XAxis.XAxisPosition.BOTTOM
            chart.xAxis.textSize=15f
            chart.xAxis.granularity=1f
            chart.xAxis.valueFormatter=MonthAxisValueFormatter()

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



    Scaffold(
        bottomBar = {
            BottomBar(
                onStatisticsCLick = onStatisticsCLick,
                onIncomeClick = onIncomeClick,
                onExpenseClick = onExpenseClick
            )
        }
    ) {
        Column {
            if(xIncomeData.isNotEmpty()&&yIncomeData.isNotEmpty()){
                LineGraph(xData =xIncomeData, yData = yIncomeData , dataLabel ="", modifier = Modifier
                    .padding(it)
                    .height(300.dp) )
            }
            Log.d("graph", xExpenseData.toString())
            if(xExpenseData.isNotEmpty()&&yExpenseData.isNotEmpty()){
                LineGraph(xData =xExpenseData, yData = yExpenseData , dataLabel ="", modifier = Modifier
                    .padding(it)
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