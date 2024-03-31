package rs.ac.ni.pmf.android.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.model.Expense

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * from expense WHERE id = :id")
    fun getExpense(id: Int): Flow<Expense>

    @Query("SELECT * from expense ORDER BY id DESC")
    fun getAllExpenses(): Flow<List<Expense>>
}