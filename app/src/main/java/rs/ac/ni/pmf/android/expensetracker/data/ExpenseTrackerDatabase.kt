package rs.ac.ni.pmf.android.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.ac.ni.pmf.android.expensetracker.model.Expense

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseTrackerDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

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
                    .build()
                    .also { Instance = it }
            }
        }
    }
}