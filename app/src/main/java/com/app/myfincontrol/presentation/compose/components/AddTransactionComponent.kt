package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import java.math.BigDecimal


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTransactionComponent(
    onEvents: (TransactionEvents) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val dropdownMenuScrollState = rememberScrollState()
    val dropdownMenuState = remember { mutableStateOf(false) }
    val steep = remember { mutableIntStateOf(0) }
    val selectedType = remember { mutableStateOf(TransactionType.INCOME) }
    val selectedCategory = remember { mutableStateOf<TransactionCategories>(TransactionCategories.IncomeCategories.INVESTMENTS) }
    var amount by rememberSaveable { mutableStateOf(BigDecimal.ZERO) }
        val categoriesIncome: List<TransactionCategories> = remember {
        mutableListOf<TransactionCategories>().apply {
            addAll(TransactionCategories.IncomeCategories.values())
        }
    }

    val categoriesExpense: List<TransactionCategories> = remember {
        mutableListOf<TransactionCategories>().apply {
            addAll(TransactionCategories.ExpenseCategories.values())
        }
    }


    Column {
        when (steep.intValue) {
            0 -> {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.add_transaction)
                        )
                    }
                    Row () {
                        Text(
                            text = stringResource(id = R.string.add_transaction_description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                    ) {
                        Button(onClick = {
                            steep.intValue = 1
                        }) {
                            Text(text = stringResource(id = R.string.done))
                        }
                    }
                }
            }
            1 -> {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.type_transaction)
                        )
                    }
                    Row () {
                        Text(
                            text = stringResource(id = R.string.type_transaction_description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            TextButton(
                                border = if (selectedType.value == TransactionType.INCOME) BorderStroke(
                                    width = 2.dp,
                                    color = Color(0xFF288B06)
                                ) else BorderStroke(
                                    width = 0.dp,
                                    color = Color.Transparent
                                ),
                                onClick = {
                                    selectedType.value = TransactionType.INCOME
                                    selectedCategory.value = TransactionCategories.IncomeCategories.INVESTMENTS
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.income),
                                    color = Color(0xFF288B06)
                                )
                            }
                        }
                        Column() {
                            TextButton(
                                border = if (selectedType.value == TransactionType.EXPENSE) BorderStroke(
                                    width = 2.dp,
                                    color = Color(0xFF991E0D)
                                ) else BorderStroke(
                                    width = 0.dp,
                                    color = Color.Transparent
                                ),
                                onClick = {
                                    selectedType.value = TransactionType.EXPENSE
                                    selectedCategory.value = TransactionCategories.ExpenseCategories.TRANSPORTATION
                                }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.expense),
                                    color = Color(0xFF991E0D)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 0
                            }) {
                                Text(text = stringResource(id = R.string.back))
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 32.dp)
                        ) {
                            Button(onClick = {
                                steep.intValue = 2
                            }) {
                                Text(text = stringResource(id = R.string.done))
                            }
                        }
                    }
                }
            }
            2 -> {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.category_transaction)
                        )
                    }
                    Row () {
                        Text(
                            text = stringResource(id = R.string.category_transaction_description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = {
                            dropdownMenuState.value = !dropdownMenuState.value
                        }) {
                            Text(text = stringResource(
                                id = categoriesTranscript(
                                    category = selectedCategory.value
                                )
                            ))
                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        DropdownMenu(
                            expanded = dropdownMenuState.value,
                            onDismissRequest = { dropdownMenuState.value = !dropdownMenuState.value },
                            scrollState = dropdownMenuScrollState
                        ) {
                            if (selectedType.value == TransactionType.INCOME) {
                                categoriesIncome.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(
                                            id = categoriesTranscript(
                                                category = it
                                            )
                                        ))},
                                        onClick = { selectedCategory.value = it }
                                    )
                                }
                            } else {
                                categoriesExpense.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(
                                            id = categoriesTranscript(
                                                category = it
                                            )
                                        ))},
                                        onClick = { selectedCategory.value = it }
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 1
                            }) {
                                Text(text = stringResource(id = R.string.back))
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 32.dp)
                        ) {
                            Button(onClick = {
                                steep.intValue = 3
                            }) {
                                Text(text = stringResource(id = R.string.done))
                            }
                        }
                    }
                }
            }
            3 -> {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.amount)
                        )
                    }
                    Row () {
                        Text(
                            text = stringResource(id = R.string.amount_description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(0.8f), // use fillMaxWidth() modifier to fill the available width
                            value = amount.toPlainString(), // use toPlainString() to display the decimal point even if it's .00
                            label = {
                                Text(text = stringResource(id = R.string.amount))
                            },
                            onValueChange = { newValue ->
                                amount = try {
                                    newValue.toBigDecimal()
                                } catch (e: NumberFormatException) {
                                    BigDecimal.ZERO
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(onDone = {  keyboardController?.hide() }), // add keyboard actions if needed
                            singleLine = true, // set singleLine to true to prevent line breaks
                            maxLines = 1, // set maxLines to 1 to prevent line breaks
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 2
                            }) {
                                Text(text = stringResource(id = R.string.back))
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 32.dp)
                        ) {
                            Button(
                                enabled = amount > BigDecimal.ZERO,
                                onClick = {
                                    steep.intValue = 4
                                }) {
                                Text(text = stringResource(id = R.string.done))
                            }
                        }
                    }
                }
            }
            4 -> {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.total)
                        )
                    }
                    Row (
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.add_transaction_total,
                                stringResource(id = typeTransactionTranslate(type = selectedType.value)),
                                stringResource(id = categoriesTranscript(category = selectedCategory.value)),
                                amount.toString()
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 3
                            }) {
                                Text(text = stringResource(id = R.string.back))
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 32.dp)
                        ) {
                            Button(
                                enabled = amount > BigDecimal.ZERO,
                                onClick = {
                                    onEvents(TransactionEvents.AddTransaction(
                                        type = selectedType.value,
                                        category = selectedCategory.value, // todo
                                        amount = amount
                                    ))
                                }) {
                                Text(text = stringResource(id = R.string.done))
                            }
                        }
                    }
                }
            }
            else -> {
                Text(text = stringResource(id = R.string.error))
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddTransactionComponentPreview() {
    AddTransactionComponent(onEvents = { })
}