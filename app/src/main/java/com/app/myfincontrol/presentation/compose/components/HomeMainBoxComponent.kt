package com.app.myfincontrol.presentation.compose.components

import android.icu.math.BigDecimal
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.Currency
import com.app.myfincontrol.data.TransactionCategories
import com.app.myfincontrol.data.TransactionType
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainBoxComponent(
    profileName: String,
    balance: BigDecimal,
    currency: Currency,
    onEventsTransaction: (TransactionEvents) -> Unit,
) {
    val showBalance = remember { mutableStateOf(true) }
    val currencySymbol = when (currency) {
        Currency.USD -> "$"
        Currency.EUR -> "€"
        Currency.RUB -> "₽"
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 150.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF0D4E42)),
                    startY = 0f,
                    endY = 400f
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        text =  " ${if (showBalance.value) balance else "***"} $currencySymbol",
                        fontSize = 42.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight(500)
                    )
                }
                Column() {
                    PlainTooltipBox(
                        tooltip = { Text(text = "Вы можете скрыть баланс") },
                    ) {
                        IconButton(onClick = {
                            showBalance.value = !showBalance.value
                        }) {
                            Icon(
                                painter = painterResource(id = if (showBalance.value) R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24),
                                contentDescription = "Visibility",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }   
                    }
                }
            }
            Row {
                Text(
                    text = profileName,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Column() {
                    TextButton(onClick = {
                        onEventsTransaction(TransactionEvents.AddTransaction(
                            Transaction(
                                amount = BigDecimal.valueOf((0..1000).random().toLong()),
                                type = TransactionType.INCOME,
                                datetime = System.currentTimeMillis(),
                                category = TransactionCategories.IncomeCategories.SALARY.name,
                                profileId = 1 // todo
                            )
                        ))
                    }) {
                        Text(text = "Добавить доход")
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Добавить расход")
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                            contentDescription = "Update",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}