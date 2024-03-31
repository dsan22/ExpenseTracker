package rs.ac.ni.pmf.android.expensetracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rs.ac.ni.pmf.android.expensetracker.model.Income

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(income: Income)

    @Update
    suspend fun update(income: Income)

    @Delete
    suspend fun delete(income: Income)

    @Query("SELECT * from income WHERE id = :id")
    fun getIncome(id: Int): Flow<Income>

    @Query("SELECT * from income ORDER BY id DESC")
    fun getAllIncome(): Flow<List<Income>>
}