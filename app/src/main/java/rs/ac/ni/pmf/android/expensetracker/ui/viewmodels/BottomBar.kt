package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.ac.ni.pmf.android.expensetracker.R

@Composable
fun BottomBar(
    onStatisticsCLick:()->Unit,
    onIncomeClick:()->Unit,
    onExpenseClick:()->Unit,
    modifier:Modifier=Modifier) {
    Row(modifier=modifier.shadow(
        shape = RectangleShape,
        elevation = 1.dp
    )) {
        Spacer(modifier =Modifier.weight(1f) )
        Image(modifier=Modifier.weight(1f).padding(10.dp).clickable { onStatisticsCLick() }, painter = painterResource(id= R.drawable.line_chart) , contentDescription ="Graphic icon" )
        Spacer(modifier =Modifier.weight(0.5f) )
        Image(modifier=Modifier.weight(1f).padding(10.dp).clickable { onExpenseClick() },painter = painterResource(id= R.drawable.expense) , contentDescription ="Graphic icon" )
        Spacer(modifier =Modifier.weight(0.5f) )
        Image(modifier=Modifier.weight(1f).padding(10.dp).clickable { onIncomeClick() },painter = painterResource(id= R.drawable.income) , contentDescription ="Graphic icon" )
        Spacer(modifier =Modifier.weight(1f) )
    }
}

@Composable
@Preview
fun BottomBarPreview(){
    BottomBar(onExpenseClick = {}, onStatisticsCLick = {}, onIncomeClick ={} )
}