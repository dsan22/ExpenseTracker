package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import rs.ac.ni.pmf.android.expensetracker.data.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.model.Expense

data class ExpenseListUiState(
    val expenses: List<Expense> = listOf()
)

class ExpenseListViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    private val timeout = 5_000L
    val uiState: StateFlow<ExpenseListUiState> =
        expenseRepository.getAllExpensesStream().map { ExpenseListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(timeout),
                initialValue = ExpenseListUiState()
            )
}
