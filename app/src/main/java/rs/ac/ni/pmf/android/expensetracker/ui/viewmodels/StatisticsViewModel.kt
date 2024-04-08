package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import rs.ac.ni.pmf.android.expensetracker.data.repository.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.IncomeRepository
import java.lang.IllegalArgumentException
import java.text.DateFormat
import java.time.Month
import java.util.Date
import java.util.Locale

data class IncomeByMonthUiState(
    val incomeByMonth:MutableMap <Int,Double>,
)
data class  ExpenseByMonthUiSate(
    val expensesByMonth:MutableMap <Int,Double>,

    )

data class Data(
    val month: Int,
    val value:Double
)
class StatisticsViewModel(private val incomeRepository: IncomeRepository,private val expenseRepository: ExpenseRepository) : ViewModel() {
    private val timeout = 5_000L
    val incomeUiState: StateFlow<IncomeByMonthUiState> = incomeRepository.getAllIncomeStream()
        .map { incomes ->
            incomes.map { income ->
                Data(
                    month = getDate(income.date).month,
                    value = income.income
                )
            }.groupBy { it.month }
                .mapValues { (_, items) -> items.sumOf { it.value } }
                .toMutableMap()
        }
        .map { IncomeByMonthUiState(incomeByMonth = it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(timeout),
            initialValue = IncomeByMonthUiState( incomeByMonth = mutableMapOf())
        )
    val expenseUiState: StateFlow<ExpenseByMonthUiSate> = expenseRepository.getAllExpensesStream()
        .map { expense ->
            expense.map { expense ->
                Data(
                    month = getDate(expense.date).month,
                    value = expense.expense
                )
            }.groupBy { it.month }
                .mapValues { (_, items) -> items.sumOf { it.value } }
                .toMutableMap()
        }
        .map { ExpenseByMonthUiSate(expensesByMonth = it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(timeout),
            initialValue = ExpenseByMonthUiSate( expensesByMonth = mutableMapOf())
        )

    private fun getDate(dateString: String): Date {
        val dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH)
        return dateFormat.parse(dateString)
            ?: throw IllegalArgumentException("Date is not in valid format")
    }
}



