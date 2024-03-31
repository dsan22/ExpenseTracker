package rs.ac.ni.pmf.android.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.ac.ni.pmf.android.expensetracker.data.dao.ExpenseDao
import rs.ac.ni.pmf.android.expensetracker.data.dao.IncomeDao
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import rs.ac.ni.pmf.android.expensetracker.model.Income

@Database(entities = [Expense::class, Income::class], version = 2, exportSchema = false)
abstract class ExpenseTrackerDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun incomeDao(): IncomeDao


    companion object {
        @Volatile
        private var Instance: ExpenseTrackerDatabase? = null

        fun getDatabase(context: Context): ExpenseTrackerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ExpenseTrackerDatabase::class.java,
                    "expense_tracker_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}