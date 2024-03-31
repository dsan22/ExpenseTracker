package rs.ac.ni.pmf.android.expensetracker.ui.viewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import rs.ac.ni.pmf.android.expensetracker.ExpenseApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            ExpenseEntryViewModel(expenseApplication().container.expenseRepository)
        }
        initializer {
            ExpenseListViewModel(expenseApplication().container.expenseRepository)
        }
    }
}

fun CreationExtras.expenseApplication(): ExpenseApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)