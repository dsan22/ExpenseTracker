package rs.ac.ni.pmf.android.expensetracker.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.getIconFromCategory
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.ExpenseDetails
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.ExpenseEntryViewModel
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.IncomeDetails
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.IncomeEntryViewModel
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun AddIncome(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: IncomeEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val incomeDetails: IncomeDetails = viewModel.incomeUiState.incomeDetails
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = incomeDetails.description,
            onValueChange = {
                viewModel.updateUiState(incomeDetails.copy(description = it))
            },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            value = incomeDetails.expense,
            onValueChange = { viewModel.updateUiState(incomeDetails.copy(expense = it)) },
            label = { Text("Expense") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        MyDatePickerDialog(date = incomeDetails.date) {
            viewModel.updateUiState(incomeDetails.copy(date = it))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveExpense()
                    }
                    navigateBack()
                }
            ) {
                Text("Confirm")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                onClick = { navigateBack() })
            {
                Text("Back")
            }
        }
    }
}




@Preview
@Composable
fun AddIncomePreview() {
    AddIncome(
        navigateBack = {},
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    )
}


