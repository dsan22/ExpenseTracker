package rs.ac.ni.pmf.android.expensetracker.data

import android.content.Context

interface AppContainer {
    val expenseRepository: ExpenseRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val expenseRepository: ExpenseRepository by lazy {
        OfflineExpensesRepository(ExpenseTrackerDatabase.getDatabase(context).expenseDao())
    }
}