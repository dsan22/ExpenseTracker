package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import rs.ac.ni.pmf.android.expensetracker.data.repository.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.IncomeRepository
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import rs.ac.ni.pmf.android.expensetracker.model.Income

data class IncomeListUiState(
    val income: List<Income> = listOf()
)

class IncomeListViewModel(private val incomeRepository: IncomeRepository) : ViewModel() {
    private val timeout = 5_000L
    val uiState: StateFlow<IncomeListUiState> =
        incomeRepository.getAllIncomeStream().map { IncomeListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(timeout),
                initialValue = IncomeListUiState()
            )
}