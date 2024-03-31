package rs.ac.ni.pmf.android.expensetracker.data.repository

import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.data.dao.ExpenseDao
import rs.ac.ni.pmf.android.expensetracker.data.dao.IncomeDao
import rs.ac.ni.pmf.android.expensetracker.model.Income

class OfflineIncomeRepository (private val incomeDao: IncomeDao) : IncomeRepository {
    override fun getAllIncomeStream(): Flow<List<Income>> {
        return incomeDao.getAllIncome()
    }

    override fun getIncomeStream(id: Int): Flow<Income?> {
       return incomeDao.getIncome(id)
    }

    override suspend fun insertIncome(income: Income) {
        incomeDao.insert(income)
    }

    override suspend fun deleteIncome(income: Income) {
        incomeDao.delete(income)
    }

    override suspend fun updateIncome(income: Income) {
        incomeDao.update(income)
    }

}