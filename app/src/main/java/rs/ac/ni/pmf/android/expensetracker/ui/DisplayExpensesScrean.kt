package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    list: List<Expense>,
    onAddExpenseClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExpenseListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = {
        LargeFloatingActionButton(
            shape = CircleShape,
            onClick = {
                onAddExpenseClicked()
            }
        ) {
            Icon(Icons.Filled.Add, "Add new Expense")
        }
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = modifier) {
                items(uiState.expenses) { expense ->
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
    val list = mutableListOf(
        Expense(1, Category.FOOD, "Dorucak", 120.0),
        Expense(2, Category.BILLS, "Struja", 2000.0),
        Expense(3, Category.BILLS, "Internet", 2500.0),
        Expense(4, Category.CLOTHS, "Patike", 3500.0),
    )
    for (i in 1..5) {
        list += list
    }
    ExpenseTrackerTheme {
        ExpenseList(
            list = list,
            onAddExpenseClicked = {},
            modifier = Modifier.fillMaxHeight())
    }
}