package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

enum class Screans(val title:String){
    EXPENSES("Expense list"),
    ADD_EXPENSE("Add expense")
}
@Composable
fun AppNavigation(navController:NavHostController){
    NavHost(navController =navController , startDestination = Screans.EXPENSES.title  ){
        composable(route = Screans.EXPENSES.title) {
            ExpenseList(onAddExpenseClicked ={ navController.navigate(Screans.ADD_EXPENSE.title)} ,modifier = Modifier.fillMaxHeight())
        }
        composable(route = Screans.ADD_EXPENSE.title) {
            AddExpense(
                onConfirmClicked = {navController.navigate(Screans.EXPENSES.title)},
                 onBackClicked = {navController.navigate(Screans.EXPENSES.title)},
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            )
        }
    }
}