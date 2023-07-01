package com.app.myfincontrol.presentation.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.enums.Currency
import com.app.myfincontrol.presentation.viewModels.events.LoginEvents

@Composable
fun CreateProfileComponent(
    onEvents: (LoginEvents) -> Unit
) {
    val accountName = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val accountNameMaxChar = Configuration.Limits.LIMIT_CHARS_NAME_PROFILE

    val selectedCurrency = remember {
        mutableStateOf(Currency.USD)
    }

    LaunchedEffect(true) {
        accountName.value = TextFieldValue("Счёт #1 ${selectedCurrency.value}")
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Добро пожаловать!",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = "Для продолжения необходимо дать название новому профилю и выбрать валюту. Обратите внимание, что после создания счета изменить валюту будет невозможно.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = accountName.value,
                label = {
                    Text(text = "Название профиля")
                },
                maxLines = 1,
                onValueChange = {
                    if (it.text.length <= accountNameMaxChar) accountName.value = it
                },
                trailingIcon = {
                    AnimatedVisibility(visible = accountName.value.text.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                accountName.value = TextFieldValue("")
                            },
                            imageVector = Icons.Rounded.Clear,
                            contentDescription = "Clear"
                        )
                    }
                },
                supportingText = {
                    Text(
                        text = "Осталось: ${accountNameMaxChar - accountName.value.text.length} символов",
                        color = if ((accountNameMaxChar - accountName.value.text.length) <= 3) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onTertiary,
                        fontSize = 12.sp
                    )
                },
                keyboardOptions = KeyboardOptions.Default,
                isError = accountName.value.text.length >= accountNameMaxChar
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Currency.values().take(3).forEach { currency ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    TextButton(
                        onClick = {
                            selectedCurrency.value = currency
                        },
                        border = if (selectedCurrency.value == currency) BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        ) else BorderStroke(
                            width = 0.dp,
                            color = Color.Transparent
                        )
                    ) {
                        Text(text = "$currency")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Other")
                }
            }
        }

        AnimatedVisibility(visible = accountName.value.text.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        onEvents.invoke(LoginEvents.CreateAccount(
                            profile = Profile(
                                name = accountName.value.text,
                                currency = selectedCurrency.value
                            )
                        )
                        )
                    },
                    enabled = accountName.value.text.isNotEmpty()
                ) {
                    Text(text = "Продолжить")
                }
            }
        }
    }
}