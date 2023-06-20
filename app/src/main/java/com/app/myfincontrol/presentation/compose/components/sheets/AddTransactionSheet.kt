package com.app.myfincontrol.presentation.compose.components.sheets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.app.myfincontrol.R
import com.app.myfincontrol.presentation.compose.components.HeaderComponent
import com.example.compose.FinControlTheme

@Composable
fun AddTransactionSheet(

) {

    val dropdownMenuScrollState = rememberScrollState()
    val dropdownMenuState = remember { mutableStateOf(false) }
    val steep = remember {
        mutableIntStateOf(0)
    }

    Box(
        modifier = Modifier
            .heightIn(min = 500.dp)
            //.padding(32.dp)
    ) {
        when (steep.intValue) {
            0 -> {
                Column(
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
                            style = MaterialTheme.typography.titleLarge
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
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = stringResource(id = R.string.income))
                            }
                        }
                        Column() {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = stringResource(id = R.string.expense))
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row() {
                        HeaderComponent(
                            title = stringResource(id = R.string.type_transaction),
                            textStyle = MaterialTheme.typography.titleLarge
                        )
                    }
                    Row {
                        TextButton(onClick = {
                            dropdownMenuState.value = !dropdownMenuState.value
                        }) {
                            Text(text = "{category} ${dropdownMenuState.value}")
                        }
                        DropdownMenu(
                            expanded = dropdownMenuState.value,
                            onDismissRequest = { dropdownMenuState.value = !dropdownMenuState.value },
                            scrollState = dropdownMenuScrollState
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = "test")
                                },
                                onClick = { /*TODO*/ }
                            )
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
                            value = TextFieldValue(),
                            label = {
                                Text(text = stringResource(id = R.string.amount))
                            },
                            onValueChange = {

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
                            text = stringResource(id = R.string.add_transaction_total, "transaction", "{category}", 0, "{currency}"),
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
                                enabled = false,
                                onClick = {

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

@Preview(showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun AddTransactionSheetPreviewLightMode() {
    FinControlTheme() {
        AddTransactionSheet()
    }
}

//@Preview(showSystemUi = true, showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
//)
//@Composable
//fun AddTransactionSheetPreviewDarkMode() {
//    FinControlTheme() {
//        AddTransactionSheet()
//    }
//}