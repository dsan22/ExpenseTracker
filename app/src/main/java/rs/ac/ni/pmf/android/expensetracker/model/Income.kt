package rs.ac.ni.pmf.android.expensetracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class Income (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val description: String,
    val income: Double,
    //val date:LocalDate=LocalDate.of(2000,10,10)
    val date: String = ""
)