package rs.ac.ni.pmf.android.expensetracker.data.repository

import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.model.Expense

interface ExpenseRepository {
    fun getAllExpensesStream(): Flow<List<Expense>>
    fun getExpenseStream(id: Int): Flow<Expense?>
    suspend fun insertExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
}