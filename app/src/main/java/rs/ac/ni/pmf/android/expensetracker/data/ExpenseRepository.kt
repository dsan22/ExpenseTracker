package rs.ac.ni.pmf.android.expensetracker.data

import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.model.Expense

interface ExpenseRepository {
    fun getAllExpensesStream(): Flow<List<Expense>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getExpenseStream(id: Int): Flow<Expense?>

    /**
     * Insert item in the data source
     */
    suspend fun insertExpense(item: Expense)

    /**
     * Delete item from the data source
     */
    suspend fun deleteExpense(item: Expense)

    /**
     * Update item in the data source
     */
    suspend fun updateExpense(item: Expense)
}