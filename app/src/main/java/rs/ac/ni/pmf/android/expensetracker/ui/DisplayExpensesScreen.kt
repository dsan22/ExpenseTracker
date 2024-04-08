package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rs.ac.ni.pmf.android.expensetracker.R
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import rs.ac.ni.pmf.android.expensetracker.model.getIconFromCategory
import rs.ac.ni.pmf.android.expensetracker.ui.theme.ExpenseTrackerTheme
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.ExpenseListViewModel

@Composable
fun ExpenseItem(expense: Expense, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxHeight()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = getIconFromCategory(expense.category)),
                contentDescription = expense.category.toString()
            )
        }
        Column(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = expense.description)
        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = expense.expense.toString())
        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = expense.date)
        }
    }
}

@Composable
fun ExpenseList(
    modifier: Modifier = Modifier,
    list: List<Expense>,
) {
    Scaffold() { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(modifier=Modifier.height(40.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {

                }
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource( R.string.description))
                }
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource( R.string.expense))
                }
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource( R.string.date))
                }
            }
            LazyColumn(modifier = modifier.weight(1f)) {
                items(list) { expense ->
                    ExpenseItem(
                        expense = expense, modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    )
                    HorizontalDivider(thickness = 1.dp)
                }
            }
        }


    }
}


@Composable
fun ExpenseListScreen(
    onAddClicked: () -> Unit ,
    onStatisticsCLick:()->Unit,
    onIncomeClick:()->Unit,
    onExpenseClick:()->Unit,
    modifier: Modifier=Modifier,
    viewModel: ExpenseListViewModel= viewModel(factory =AppViewModelProvider.Factory)
){
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = {
            BottomBar(
                modifier=Modifier.fillMaxWidth(),
                onStatisticsCLick = onStatisticsCLick,
                onIncomeClick = onIncomeClick,
                onExpenseClick = onExpenseClick
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                shape = CircleShape,
                onClick = {
                    onAddClicked()
                }
            ) {
                Icon(Icons.Filled.Add, "Add new Expense")
            }
        }
    ) {
        ExpenseList( list =uiState.expenses, modifier = modifier.padding(it) )
    }
    
}

@Preview(showBackground = true)
@Composable
fun ExpenseItemPreview() {
    ExpenseTrackerTheme {
        ExpenseItem(
            expense = Expense(1, Category.CLOTHS, "test", 125.0, date = "2020-10-10"),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseListPreview() {
    ExpenseTrackerTheme { ExpenseList(
            list = listOf(
                Expense(1,Category.FOOD,"Rucak",200.0,"March 20, 2024"),
                Expense(1,Category.CLOTHS,"Majica",1500.0,"March 25, 2024"),
                Expense(1,Category.BILLS,"Racun za struju",2500.0,"March 20, 2024"),
                Expense(1,Category.BILLS,"Racun za internet",2000.0,"March 20, 2024")
            ),
            modifier = Modifier.fillMaxHeight())
    }
}