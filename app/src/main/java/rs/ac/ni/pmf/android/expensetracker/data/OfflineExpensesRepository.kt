package rs.ac.ni.pmf.android.expensetracker.data

import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.model.Expense

class OfflineExpensesRepository(private val expenseDao: ExpenseDao) : ExpenseRepository {
    override fun getAllExpensesStream(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }

    override fun getExpenseStream(id: Int): Flow<Expense?> {
        return expenseDao.getExpense(id)
    }

    override suspend fun insertExpense(item: Expense) {
        expenseDao.insert(item)
    }

    override suspend fun deleteExpense(item: Expense) {
        expenseDao.delete(item)
    }

    override suspend fun updateExpense(item: Expense) {
        expenseDao.update(item)
    }
}