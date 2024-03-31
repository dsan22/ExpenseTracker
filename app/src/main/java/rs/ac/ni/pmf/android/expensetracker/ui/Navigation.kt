package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModel

enum class Screens(val title: String) {
    EXPENSES("Expense list"),
    ADD_EXPENSE("Add expense")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    appViewModel: AppViewModel = viewModel()
) {
    val uiState by appViewModel.uiState.collectAsState()
    NavHost(navController = navController, startDestination = Screens.EXPENSES.title) {
        composable(route = Screens.EXPENSES.title) {
            ExpenseList(
                list = uiState.expenses,
                onAddExpenseClicked = { navController.navigate(Screens.ADD_EXPENSE.title) },
                modifier = Modifier.fillMaxHeight())
        }
        composable(route = Screens.ADD_EXPENSE.title) {
            AddExpense(
                navigateBack = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            )
        }
    }
}