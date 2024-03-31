package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

enum class Screens(val title: String) {
    EXPENSES("Expense list"),
    ADD_EXPENSE("Add expense"),
    INCOME("Income list"),
    ADD_INCOME("Add income"),
    STATISTICS("Show basic statistics")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screens.INCOME.title) {
        composable(route = Screens.EXPENSES.title) {
            ExpenseListScreen(
                onAddClicked = { navController.navigate(Screens.ADD_EXPENSE.title) },
                onExpenseClick ={} ,
                onIncomeClick = {navController.navigate(Screens.INCOME.title)},
                onStatisticsCLick = {navController.navigate(Screens.STATISTICS.title)},
                modifier = Modifier.fillMaxHeight())
        }
        composable(route = Screens.ADD_EXPENSE.title) {
            AddExpense(
                navigateBack = { navController.popBackStack() },
                modifier = Modifier.fillMaxHeight().padding(10.dp)
            )
        }
        composable(route=Screens.INCOME.title){
            IncomeListScreen(
                onAddClicked = { navController.navigate(Screens.ADD_INCOME.title) },
                onExpenseClick ={navController.navigate(Screens.EXPENSES.title)} ,
                onIncomeClick = {},
                onStatisticsCLick = {navController.navigate(Screens.STATISTICS.title)},
                modifier = Modifier.fillMaxHeight().padding(10.dp))
        }
        composable(route = Screens.ADD_INCOME.title) {
            AddIncome(
                navigateBack = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            )
        }
        composable(route=Screens.STATISTICS.title){
            Text(text = "work in progress")
        }
    }
}