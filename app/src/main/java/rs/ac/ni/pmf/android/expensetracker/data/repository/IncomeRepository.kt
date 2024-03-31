package rs.ac.ni.pmf.android.expensetracker.data.repository

import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import rs.ac.ni.pmf.android.expensetracker.model.Income

interface IncomeRepository {
    fun getAllIncomeStream(): Flow<List<Income>>
    fun getIncomeStream(id: Int): Flow<Income?>
    suspend fun insertIncome(income: Income)
    suspend fun deleteIncome(income: Income)
    suspend fun updateIncome(income: Income)

}