package rs.ac.ni.pmf.android.expensetracker.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import rs.ac.ni.pmf.android.expensetracker.R
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.AppViewModelProvider
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.IncomeDetails
import rs.ac.ni.pmf.android.expensetracker.ui.viewmodels.IncomeEntryViewModel

@Composable
fun AddIncome(
    navigateBack: () -> Unit,
    windowSize: WindowWidthSizeClass,
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
            label = { Text(stringResource( R.string.description)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            value = incomeDetails.expense,
            onValueChange = { viewModel.updateUiState(incomeDetails.copy(expense = it)) },
            label = { Text(stringResource( R.string.income)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        MyDatePickerDialog(date = incomeDetails.date) {
            viewModel.updateUiState(incomeDetails.copy(date = it))
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
                                viewModel.saveIncome()
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
                                viewModel.saveIncome()
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




@Preview
@Composable
fun AddIncomePreview() {
    AddIncome(
        navigateBack = {},
        windowSize= WindowWidthSizeClass.Compact,
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    )
}


