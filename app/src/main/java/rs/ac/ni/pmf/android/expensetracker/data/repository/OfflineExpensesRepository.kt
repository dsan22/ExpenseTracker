package rs.ac.ni.pmf.android.expensetracker.data.repository

import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.data.dao.ExpenseDao
import rs.ac.ni.pmf.android.expensetracker.data.repository.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.model.Expense

class OfflineExpensesRepository(private val expenseDao: ExpenseDao) : ExpenseRepository {
    override fun getAllExpensesStream(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }

    override fun getExpenseStream(id: Int): Flow<Expense?> {
        return expenseDao.getExpense(id)
    }

    override suspend fun insertExpense(expense: Expense) {
        expenseDao.insert(expense)
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.delete(expense)
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.update(expense)
    }
}