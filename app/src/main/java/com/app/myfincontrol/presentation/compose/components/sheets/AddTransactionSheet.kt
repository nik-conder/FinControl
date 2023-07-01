package com.app.myfincontrol.presentation.compose.components.sheets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.compose.components.HeaderComponent
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import java.math.BigDecimal

@Composable
fun AddTransactionSheet(
    onEvents: (TransactionEvents) -> Unit
) {

    val dropdownMenuScrollState = rememberScrollState()
    val dropdownMenuState = remember { mutableStateOf(false) }
    val steep = remember {
        mutableIntStateOf(0)
    }

    val selectedTransaction = remember { mutableStateOf(TransactionType.INCOME) }

    val selectedCategory = remember { mutableStateOf<TransactionCategories>(TransactionCategories.IncomeCategories.INVESTS) }

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

    var amount by remember { mutableStateOf(BigDecimal("0")) }

    Box(
        modifier = Modifier
            .heightIn(min = 500.dp)
            //.padding(32.dp)
    ) {
        when (steep.intValue) {
            0 -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(title = stringResource(id = R.string.add_transaction_content_description))
                    }
                    Row (
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_transaction_description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 64.dp)
                    ) {
                        Button(onClick = {
                            steep.intValue = 1
                        }) {
                            Text(text = "Далее")
                        }
                    }
                }
            }
            1 -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.type_transaction),
                            textStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row {
                        Column() {
                            TextButton(
                                border = if (selectedTransaction.value == TransactionType.INCOME) BorderStroke(
                                    width = 2.dp,
                                    color = Color(0xFF288B06)
                                ) else BorderStroke(
                                    width = 0.dp,
                                    color = Color.Transparent
                                ),
                                onClick = {
                                    selectedTransaction.value = TransactionType.INCOME
                                    selectedCategory.value = TransactionCategories.IncomeCategories.INVESTS
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
                                border = if (selectedTransaction.value == TransactionType.EXPENSE) BorderStroke(
                                    width = 2.dp,
                                    color = Color(0xFF991E0D)
                                ) else BorderStroke(
                                    width = 0.dp,
                                    color = Color.Transparent
                                ),
                                onClick = {
                                    selectedTransaction.value = TransactionType.EXPENSE
                                    selectedCategory.value = TransactionCategories.ExpenseCategories.TRANSPORT
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
                            .padding(top = 64.dp)
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 0
                            }) {
                                Text(text = "Назад")
                            }
                        }
                        Column() {
                            Button(onClick = {
                                steep.intValue = 2
                            }) {
                                Text(text = "Далее")
                            }
                        }
                    }
                }
            }
            2 -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.category_transaction),
                            textStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row {
                        Text(text = selectedCategory.value.toString())
                    }
                    Row {
                        TextButton(onClick = {
                            dropdownMenuState.value = !dropdownMenuState.value
                        }) {
                            Text(text = selectedCategory.value.toString())
                        }
                        DropdownMenu(
                            expanded = dropdownMenuState.value,
                            onDismissRequest = { dropdownMenuState.value = !dropdownMenuState.value },
                            scrollState = dropdownMenuScrollState
                        ) {
                            if (selectedTransaction.value == TransactionType.INCOME) {
                                categoriesIncome.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = it.toString()) },
                                        onClick = { selectedCategory.value = it }
                                    )
                                }
                            } else {
                                categoriesExpense.forEach {
                                    DropdownMenuItem(
                                        text = { Text(text = it.toString()) },
                                        onClick = { selectedCategory.value = it }
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 64.dp)
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 1
                            }) {
                                Text(text = "Назад")
                            }
                        }
                        Column() {
                            Button(onClick = {
                                steep.intValue = 3
                            }) {
                                Text(text = "Далее")
                            }
                        }
                    }
                }
            }
            3 -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.amount),
                            textStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = amount.toString(),
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 64.dp)
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 2
                            }) {
                                Text(text = "Назад")
                            }
                        }
                        Column() {
                            Button(
                                //enabled = false,
                                onClick = {
                                    steep.intValue = 4
                            }) {
                                Text(text = "Далее")
                            }
                        }
                    }
                }
            }
            4 -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.total),
                            textStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row (
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = "Amount: ${amount}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(
                        modifier = Modifier
                        .padding(top = 64.dp)
                    ) {
                        Column() {
                            Button(onClick = {
                                steep.intValue = 3
                            }) {
                                Text(text = "Назад")
                            }
                        }
                        Column() {
                            Button(
                                enabled = amount > BigDecimal.ZERO,
                                onClick = {
                                    onEvents(TransactionEvents.AddTransaction(
                                        type = selectedTransaction.value,
                                        category = selectedCategory.value, // todo
                                        amount = amount
                                    ))
                                }) {
                                Text(text = "Готово!")
                            }
                        }
                    }
                }
            }
            else -> {
                Text(text = "Ошибка")
            }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
//)
//@Composable
//fun AddTransactionSheetPreviewLightMode() {
//    FinControlTheme() {
//        AddTransactionSheet()
//    }
//}

//@Preview(showSystemUi = true, showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
//)
//@Composable
//fun AddTransactionSheetPreviewDarkMode() {
//    FinControlTheme() {
//        AddTransactionSheet()
//    }
//}