package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import rs.ac.ni.pmf.android.expensetracker.model.Expense

data class AppState(
    var expenses: List<Expense> = emptyList(),
    var test: String = "TEST"
)


class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()
    val list = uiState.value.expenses.toMutableList()
    var test by mutableStateOf(_uiState.value.test)

    fun addExpense(expense: Expense) {
        val currentState = _uiState.value
        val updatedExpenses = currentState.expenses.toMutableList().apply {
            add(expense)
        }
        list.add(expense);
        test = expense.description;
        _uiState.update {
            it.copy(expenses = updatedExpenses, test = test)
        }
    }

}