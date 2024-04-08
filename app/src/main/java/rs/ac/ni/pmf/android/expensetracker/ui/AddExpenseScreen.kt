package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import rs.ac.ni.pmf.android.expensetracker.R
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.getIconFromCategory
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.ExpenseDetails
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.ExpenseEntryViewModel
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddExpense(
    navigateBack: () -> Unit,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: ExpenseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val expenseDetails: ExpenseDetails = viewModel.expenseUiState.expenseDetails
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CategoryDropDownMenu(category = expenseDetails.category) {
            viewModel.updateUiState(expenseDetails.copy(category = it))
        }

        TextField(
            value = expenseDetails.description,
            onValueChange = {
                viewModel.updateUiState(expenseDetails.copy(description = it))
            },
            label = { Text(stringResource( R.string.description)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            value = expenseDetails.expense,
            onValueChange = { viewModel.updateUiState(expenseDetails.copy(expense = it)) },
            label = { Text(stringResource( R.string.expense)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        MyDatePickerDialog(date = expenseDetails.date) {
            viewModel.updateUiState(expenseDetails.copy(date = it))
        }
        when (windowSize) {
            WindowWidthSizeClass.Compact -> {
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
                        Text(stringResource( R.string.confirm))
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                        onClick = { navigateBack() })
                    {
                        Text(stringResource( R.string.back))
                    }
                }
            }
            WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveExpense()
                            }
                            navigateBack()
                        }
                    ) {
                        Text(stringResource( R.string.confirm))
                    }

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                        onClick = { navigateBack() })
                    {
                        Text(stringResource( R.string.back))
                    }
                }
            }
        }


    }
}

@Composable
fun CategoryDropDownMenu(category: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val categoryList: List<Category> by remember { mutableStateOf(Category.values().toList()) }

    TextField(
        enabled = false,
        value = "",
        onValueChange = { },
        label = {
            Row {
                Column(
                    modifier = Modifier.height(30.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource( R.string.category)+ ": $category", color = Color.Black)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .width(30.dp)
                        .padding(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = getIconFromCategory(Category.valueOf(category))),
                        contentDescription = category
                    )
                }

            }

        },
        trailingIcon = {
            if (!expanded) {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Category dropdown icon arrow down"
                )
            } else {
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Category dropdown icon arrow up"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                expanded = true
            },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Black
        )
    )

    Row {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categoryList.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row {
                            Column(
                                modifier = Modifier.height(30.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(item.toString())
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                modifier = Modifier
                                    .width(30.dp)
                                    .padding(5.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = getIconFromCategory(item)),
                                    contentDescription = item.toString()
                                )
                            }
                        }
                    },
                    onClick = {
                        expanded = false
                        onSelect(item.name)
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialogCalendar(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }
            ) {
                Text(text = stringResource( R.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource( R.string.cancel))
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
fun MyDatePickerDialog(date: String, onDateSelected: (String) -> Unit) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    Box(contentAlignment = Alignment.Center) {
        TextField(
            enabled = false,
            value = date,
            onValueChange = {},
            trailingIcon = {
                Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable {
                    showDialog = true
                },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Black
            )
        )
    }

    if (showDialog) {
        MyDatePickerDialogCalendar(
            onDateSelected = onDateSelected,
            onDismiss = { showDialog = false }
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH)
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun DatePreview() {
    MyDatePickerDialog(
        date = convertMillisToDate(Calendar.getInstance().timeInMillis),
        onDateSelected = {}
    )
}

@Preview
@Composable
fun AddExpensePreview() {
    AddExpense(
        navigateBack = {},
        windowSize= WindowWidthSizeClass.Compact,
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    )
}


