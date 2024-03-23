package rs.ac.ni.pmf.android.expensetracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import rs.ac.ni.pmf.android.expensetracker.model.Category
import rs.ac.ni.pmf.android.expensetracker.model.getIconFromCategory
import rs.ac.ni.pmf.android.expensetracker.R
import rs.ac.ni.pmf.android.expensetracker.model.Expense
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(
    onConfirmClicked:(expense:Expense)->Unit,
    onBackClicked:()->Unit,
    modifier:Modifier= Modifier,
){
    var description by remember { mutableStateOf("") }
    var expenseString by remember { mutableStateOf("") }
    var expense by remember { mutableStateOf(0.0)}


    var expanded by remember { mutableStateOf(false) }
    val items: List<Category> by remember {mutableStateOf(Category.values().toList()) }
    var selectedCategory: Category by remember {
        mutableStateOf(items[0])
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
           enabled = false,
            value = "",
            onValueChange = {  },
            label = {
                Row {
                    Column( modifier= Modifier.height(30.dp) ,verticalArrangement = Arrangement.Center) {
                        Text(text="Category: $selectedCategory", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier= Modifier
                        .width(30.dp)
                        .padding(5.dp)) {
                        Image( painter =  painterResource(id= getIconFromCategory(selectedCategory)),
                            contentDescription =selectedCategory.toString() )
                    }
                    Spacer(modifier = Modifier.weight(1.0f)) // fill height with spacer
                    Column(modifier= Modifier
                        .width(30.dp)
                        .padding(5.dp)
                    ) {
                        Image( painter =  painterResource(id= R.drawable.down_arrow ),
                            contentDescription =selectedCategory.toString() )
                    }

                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable {
                    expanded = true
                },
            colors =  TextFieldDefaults.colors(
                disabledTextColor = Color.Black
            )

        )

        Row {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Row {
                                Column( modifier= Modifier.height(30.dp) ,verticalArrangement = Arrangement.Center) {
                                    Text("Category: $item")
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier= Modifier
                                    .width(30.dp)
                                    .padding(5.dp)) {
                                    Image( painter =  painterResource(id= getIconFromCategory(item)),
                                        contentDescription =item.toString() )
                                }

                            }
                        },
                        onClick = {
                            selectedCategory=item
                            expanded = false
                            println(" $selectedCategory")
                        })
                }
            }
        }

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            value = expenseString,
            onValueChange = {
                try {
                    expense = it.toDouble()
                    expenseString = it
                }
                catch (_:Exception){
                }

            },
            label = { Text("Expense") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                onClick = {
                    val newExpense= Expense(category = selectedCategory, description = description, expense = expense)
                    onConfirmClicked(newExpense)
                }
            ) {
                Text("Confirm")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp) ,
                onClick = { onBackClicked()} ) {
                Text("Back")
            }

        }
    }


}



@Preview
@Composable
fun AddExpensePreview(){
    AddExpense(
        onBackClicked = {},
        onConfirmClicked = {},
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    )
}


