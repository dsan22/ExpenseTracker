package rs.ac.ni.pmf.android.expensetracker.model

import rs.ac.ni.pmf.android.expensetracker.R

enum class Category {
    FOOD,
    BILLS,
    CLOTHS,
    HOUSEHOLD_ITEMS
}

fun getIconFromCategory(c:Category): Int {
    return when(c){
        Category.BILLS-> R.drawable.bills
        Category.FOOD -> R.drawable.food
        Category.CLOTHS -> R.drawable.clothing
        Category.HOUSEHOLD_ITEMS -> R.drawable.household_items
    }
}