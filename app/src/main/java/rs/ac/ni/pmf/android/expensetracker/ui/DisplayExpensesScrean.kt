package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import rs.ac.ni.pmf.android.expensetracker.model.getIconFromCategory
import rs.ac.ni.pmf.android.expensetracker.ui.theme.ExpenseTrackerTheme

@Composable
fun ExpenseItem(expense:Expense, modifier: Modifier = Modifier) {
   Row(modifier=modifier){
        Column(
            modifier= Modifier
                .weight(0.1f)
                .fillMaxHeight()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter =  painterResource(id=getIconFromCategory(expense.category)) ,
                contentDescription =expense.category.toString()
            )
        }
       Column(
           modifier= Modifier
               .weight(0.5f)
               .fillMaxHeight(),
           verticalArrangement = Arrangement.Center
       ) {
           Text(text = expense.description)
       }
       Column (
           modifier= Modifier
               .weight(0.4f)
               .fillMaxHeight(),
           verticalArrangement = Arrangement.Center
       ){
           Text(text = expense.expense.toString())
       }
   }
}
@Composable
fun ExpenseList( modifier: Modifier = Modifier) {
    val list= mutableListOf(
        Expense(Category.FOOD,"Dorucak",120.0),
        Expense(Category.BILLS,"Struja",2000.0),
        Expense(Category.BILLS,"Internet",2500.0),
        Expense(Category.CLOTHS,"Patike",3500.0),
        )
    for(i in 1..5){
        list+=list
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        contentAlignment = Alignment.BottomEnd // Aligns the content to the bottom end (bottom right)
    ) {
        LargeFloatingActionButton(shape = CircleShape, onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Add, "Add new Expense")
        }
    }
  Column {
      LazyColumn(modifier = modifier) {
          items(list) { expense ->
              ExpenseItem(expense = expense,modifier= Modifier
                  .fillMaxWidth()
                  .height(40.dp)
              )
              Divider(thickness = 1.dp)
          }
      }

  }
}
@Preview(showBackground = true)
@Composable
fun ExpenseItemPreview() {
    ExpenseTrackerTheme {
        ExpenseItem(expense= Expense(Category.CLOTHS,"test",125.0)  , modifier= Modifier
            .fillMaxWidth()
            .height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseListPreview() {
    ExpenseTrackerTheme {
        ExpenseList(modifier = Modifier.fillMaxHeight())
    }
}