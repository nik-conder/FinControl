package com.app.myfincontrol.presentation.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import com.app.myfincontrol.R
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents
import kotlinx.coroutines.launch
import java.math.BigDecimal


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddTransactionComponent(
    sheetState: SheetState,
    feedPager: LazyPagingItems<Transaction>,
    onEvents: (TransactionEvents) -> Unit
) {

    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val dropdownMenuScrollState = rememberScrollState()
    val dropdownMenuState = remember { mutableStateOf(false) }
    val selectedType = remember { mutableStateOf(TransactionType.INCOME) }
    val selectedCategory =
        remember { mutableStateOf<TransactionCategories>(TransactionCategories.IncomeCategories.INVESTMENTS) }
    var amount by rememberSaveable { mutableStateOf(BigDecimal.ZERO) }
    var note = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val categoriesIncome: List<TransactionCategories> = remember {
        mutableListOf<TransactionCategories>().apply {
            addAll(TransactionCategories.IncomeCategories.values())
        }
    }

    val limitCharsNote = Configuration.Limits.LIMIT_CHARS_NOTE

    val categoriesExpense: List<TransactionCategories> = remember {
        mutableListOf<TransactionCategories>().apply {
            addAll(TransactionCategories.ExpenseCategories.values())
        }
    }

    val marginBoxTop = 24.dp
    val paddingColumBox = PaddingValues(start = 16.dp, end = 16.dp)
    val paddingDivider = PaddingValues(top = 8.dp, bottom = 8.dp)

    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            //.padding(paddingValues)
            .verticalScroll(scrollState)
    ) {
        val (mainBox, typeBox, categoriesBox, amountBox, buttonsBox, noteBox) = createRefs()

        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(mainBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingColumBox),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.add_transaction_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(paddingDivider)
                ) {
                    Divider(thickness = 1.dp)
                }
            }

        }

        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(typeBox) {
                    top.linkTo(mainBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingColumBox),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row {
                            Text(
                                text = stringResource(R.string.type_transaction),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row {
                            Text(
                                text = stringResource(id = R.string.type_transaction_description),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Column {
                        Row {
                            Column {
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
                                        selectedCategory.value =
                                            TransactionCategories.IncomeCategories.INVESTMENTS
                                    }
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.income),
                                        color = Color(0xFF288B06)
                                    )
                                }
                            }
                            Column {
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
                                        selectedCategory.value =
                                            TransactionCategories.ExpenseCategories.TRANSPORTATION
                                    }
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.expense),
                                        color = Color(0xFF991E0D)
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(paddingDivider)
                ) {
                    Divider(thickness = 1.dp)
                }
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(categoriesBox) {
                    top.linkTo(typeBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingColumBox),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row {
                            Text(
                                text = stringResource(R.string.category_transaction),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row {
                            Text(
                                text = stringResource(id = R.string.category_transaction_description),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Column {
                        TextButton(onClick = {
                            dropdownMenuState.value = !dropdownMenuState.value
                        }) {
                            Text(
                                text = stringResource(
                                    id = categoriesTranscript(
                                        category = selectedCategory.value
                                    )
                                )
                            )
                        }

                        DropdownMenu(
                            expanded = dropdownMenuState.value,
                            onDismissRequest = {
                                dropdownMenuState.value = !dropdownMenuState.value
                            }
                        ) {
                            if (selectedType.value == TransactionType.INCOME) {
                                categoriesIncome.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(
                                                    id = categoriesTranscript(
                                                        category = it
                                                    )
                                                )
                                            )
                                        },
                                        onClick = {
                                            selectedCategory.value = it
                                            dropdownMenuState.value = false
                                        }
                                    )
                                }
                            } else {
                                categoriesExpense.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = stringResource(
                                                    id = categoriesTranscript(
                                                        category = it
                                                    )
                                                )
                                            )
                                        },
                                        onClick = { selectedCategory.value = it }
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(paddingDivider)
                ) {
                    Divider(thickness = 1.dp)
                }
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(amountBox) {
                    top.linkTo(categoriesBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingColumBox),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row {
                            Text(
                                text = stringResource(R.string.amount),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row {
                            Text(
                                text = stringResource(id = R.string.amount_description),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Column {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(0.5f), // use fillMaxWidth() modifier to fill the available width
                            value = amount.toPlainString(), // use toPlainString() to display the decimal point even if it's .00

                            onValueChange = { newValue ->
                                amount = try {
                                    newValue.toBigDecimal()
                                } catch (e: NumberFormatException) {
                                    BigDecimal.ZERO
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }), // add keyboard actions if needed
                            singleLine = true, // set singleLine to true to prevent line breaks
                            maxLines = 1, // set maxLines to 1 to prevent line breaks,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(paddingDivider)
                ) {
                    Divider(thickness = 1.dp)
                }
            }
        }
        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(noteBox) {
                    top.linkTo(amountBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight(),
                value = note.value,
                onValueChange = {
                    if (it.text.length <= limitCharsNote) note.value = it
                },
                trailingIcon = {
                    AnimatedVisibility(visible = note.value.text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                note.value = TextFieldValue("")
                            },
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Clear"
                        )
                    }
                },
                supportingText = {
                    Text(
                        text = stringResource(
                            id = R.string.there_are_n_characters_left,
                            (limitCharsNote - note.value.text.length)
                        ),
                        color = if ((limitCharsNote - note.value.text.length) <= 10) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.note)
                    )
                },
                keyboardOptions = KeyboardOptions.Default,
                isError = note.value.text.length >= limitCharsNote,
                shape = RoundedCornerShape(10.dp)
            )
        }
        BoxWithConstraints(
            modifier = Modifier
                .constrainAs(buttonsBox) {
                    top.linkTo(noteBox.bottom, margin = marginBoxTop)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 48.dp)
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        enabled = amount > BigDecimal.ZERO,
                        onClick = {
                            onEvents.invoke(
                                TransactionEvents.AddTransaction(
                                    type = selectedType.value,
                                    category = selectedCategory.value,
                                    amount = amount,
                                    note = note.value.text
                                )
                            )
                            scope.launch {
                                sheetState.hide()
                                feedPager.refresh()
                            }
                        }) {
                        Text(text = stringResource(id = R.string.done))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddTransactionComponentPreview() {
    var note by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = note,
        onValueChange = { newValue ->
            note = try {
                newValue
            } catch (e: NumberFormatException) {
                ""
            }
        },
        label = {
            Text(
                text = stringResource(id = R.string.note)
            )
        },
        shape = RoundedCornerShape(10.dp)
    )

    /*
    OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(0.5f), // use fillMaxWidth() modifier to fill the available width
                            value = amount.toPlainString(), // use toPlainString() to display the decimal point even if it's .00

                            onValueChange = { newValue ->
                                amount = try {
                                    newValue.toBigDecimal()
                                } catch (e: NumberFormatException) {
                                    BigDecimal.ZERO
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }), // add keyboard actions if needed
                            singleLine = true, // set singleLine to true to prevent line breaks
                            maxLines = 1, // set maxLines to 1 to prevent line breaks,
                            shape = RoundedCornerShape(10.dp)
                        )
     */
}