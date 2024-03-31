package rs.ac.ni.pmf.android.expensetracker.data

import android.content.Context
import rs.ac.ni.pmf.android.expensetracker.data.repository.ExpenseRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.IncomeRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.OfflineExpensesRepository
import rs.ac.ni.pmf.android.expensetracker.data.repository.OfflineIncomeRepository

interface AppContainer {
    val expenseRepository: ExpenseRepository
    val incomeRepository:IncomeRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val expenseRepository: ExpenseRepository by lazy {
        OfflineExpensesRepository(ExpenseTrackerDatabase.getDatabase(context).expenseDao())
    }
    override val incomeRepository: IncomeRepository by lazy {
        OfflineIncomeRepository(ExpenseTrackerDatabase.getDatabase(context).incomeDao())
    }
}