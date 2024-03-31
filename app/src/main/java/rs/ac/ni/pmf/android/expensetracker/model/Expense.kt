package rs.ac.ni.pmf.android.expensetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val category: Category,
    val description: String,
    val expense: Double,
    //val date:LocalDate=LocalDate.of(2000,10,10)
    val date: String = ""
)