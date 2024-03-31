package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import rs.ac.ni.pmf.android.expensetracker.data.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import java.text.DateFormat
import java.util.Calendar
import java.util.Date


data class ExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false
)

data class ExpenseDetails(
    val id: Int = 0,
    val category: String = "FOOD",
    val description: String = "",
    val expense: String = "",
    val date: String = DateFormat.getDateInstance()
        .format(Date(Calendar.getInstance().timeInMillis))
)

fun ExpenseDetails.toExpense(): Expense = Expense(
    id = id,
    description = description,
    expense = expense.toDoubleOrNull() ?: 0.0,
    category = Category.valueOf(category),
    date = date
)

class ExpenseEntryViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    var expenseUiState by mutableStateOf(
        ExpenseUiState()
    )
        private set

    fun updateUiState(expenseDetails: ExpenseDetails) {
        expenseUiState =
            expenseUiState.copy(
                expenseDetails = expenseDetails,
                isEntryValid = validateInput(expenseDetails)
            )
    }

    suspend fun saveExpense() {
        if (validateInput()) {
            println(expenseUiState.expenseDetails.toExpense().description + " " + expenseUiState.expenseDetails.toExpense().expense)
            expenseRepository.insertExpense(expenseUiState.expenseDetails.toExpense())
        } else {
            println(
                "SAVE" + "\n" +
                        expenseUiState.expenseDetails.expense + "\n" +
                        expenseUiState.expenseDetails.description + "\n" +
                        expenseUiState.expenseDetails.category + "\n"
            )
        }

    }

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {
        return with(uiState) {
            description.isNotBlank() && expense.isNotBlank() && category.isNotBlank() //&& date.isNotBlank()
        }
    }
}