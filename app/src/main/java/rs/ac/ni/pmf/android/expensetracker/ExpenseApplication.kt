package rs.ac.ni.pmf.android.expensetracker

import android.app.Application
import rs.ac.ni.pmf.android.expensetracker.data.AppContainer
import rs.ac.ni.pmf.android.expensetracker.data.AppDataContainer

class ExpenseApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}