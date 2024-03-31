package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import rs.ac.ni.pmf.android.expensetracker.data.repository.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.IncomeRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.OfflineIncomeRepository
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import rs.ac.ni.pmf.android.expensetracker.model.Income
import java.text.DateFormat
import java.util.Calendar
import java.util.Date


data class IncomeUiState(
    val incomeDetails: IncomeDetails = IncomeDetails(),
    val isEntryValid: Boolean = false
)

data class IncomeDetails(
    val id: Int = 0,
    val description: String = "",
    val expense: String = "",
    val date: String = DateFormat.getDateInstance()
        .format(Date(Calendar.getInstance().timeInMillis))
)

fun IncomeDetails.toIncome(): Income = Income(
    id = id,
    description = description,
    income = expense.toDoubleOrNull() ?: 0.0,
    date = date
)

class IncomeEntryViewModel(private val incomeRepository: IncomeRepository) : ViewModel() {
    var incomeUiState by mutableStateOf(
        IncomeUiState()
    )
        private set

    fun updateUiState(incomeDetails: IncomeDetails) {
        incomeUiState =
            incomeUiState.copy(
                incomeDetails = incomeDetails,
                isEntryValid = validateInput(incomeDetails)
            )
    }

    suspend fun saveExpense() {
        if (validateInput()) {
            incomeRepository.insertIncome(incomeUiState.incomeDetails.toIncome())
        }
    }

    private fun validateInput(uiState: IncomeDetails = incomeUiState.incomeDetails): Boolean {
        return with(uiState) {
            description.isNotBlank() && expense.isNotBlank() && date.isNotBlank()
        }
    }
}