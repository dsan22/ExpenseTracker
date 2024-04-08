package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rs.ac.ni.pmf.android.expensetracker.R
import rs.ac.ni.pmf.android.expensetracker.model.Income
import rs.ac.ni.pmf.android.expensetracker.ui.theme.ExpenseTrackerTheme
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.IncomeListViewModel


@Composable
fun IncomeItem(income: Income, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = income.description)
        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = income.income.toString())
        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = income.date)
        }
    }
}

@Composable
fun IncomeList(
    modifier: Modifier = Modifier,
    list: List<Income>,
) {
    Scaffold() { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(modifier=Modifier.height(40.dp)) {
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
                    Text(text = stringResource( R.string.income))
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
            LazyColumn(modifier = modifier) {
                items(list) { income ->
                    IncomeItem(
                        income = income, modifier = Modifier
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
fun IncomeListScreen(
    onAddClicked: () -> Unit,
    onStatisticsCLick:()->Unit,
    onIncomeClick:()->Unit,
    onExpenseClick:()->Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: IncomeListViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    DynamicMenu(
        onStatisticsCLick = onStatisticsCLick,
        onIncomeClick = onIncomeClick,
        onExpenseClick = onExpenseClick,
        windowSize = windowSize
    ) {
        val uiState by viewModel.uiState.collectAsState()

        Scaffold(
            floatingActionButton = {
                LargeFloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        onAddClicked()
                    },
                    modifier = Modifier.padding(it)

                ) {
                    Icon(Icons.Filled.Add, "Add new Expense")
                }
            }
        ) {
            IncomeList( list = uiState.income, modifier = modifier.padding(it) )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun IncomeItemPreview() {
    ExpenseTrackerTheme {
        IncomeItem(
            income = Income(1, "test", 125.0, date = "2020-10-10"),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeListPreview() {
    ExpenseTrackerTheme { IncomeList(
        list = listOf(
            Income(1,"Plata",20000.0,"March 20, 2024"),
            Income(1,"Plata",40000.0,"March 25, 2024"),
            Income(1,"Stipendija",6000.0,"March 20, 2024"),
            Income(1,"Plata",30000.0,"March 20, 2024")
        ),
        modifier = Modifier.fillMaxHeight())
    }
}