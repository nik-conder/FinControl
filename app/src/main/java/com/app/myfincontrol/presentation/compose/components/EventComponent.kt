package com.app.myfincontrol.presentation.compose.components

import android.content.res.Configuration
import android.icu.math.BigDecimal
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.data.TransactionCategories
import com.app.myfincontrol.data.TransactionType
import com.app.myfincontrol.data.entities.Transaction
import com.example.compose.FinControlTheme
import java.util.Date

enum class TypeEvent {
    NOTIFICATION, TRANSACTION
}
@Composable
fun EventComponent(
    type: TypeEvent,
    transaction: Transaction
) {
    when (type) {
        TypeEvent.NOTIFICATION -> {
            NotificationComponent()
        }
        TypeEvent.TRANSACTION -> {
            TransactionComponent(transaction)
        }
    }
}

@Composable
fun NotificationComponent() {
    Text(text = "Notification")
}

@Composable
fun TransactionComponent(
    transaction: Transaction
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = if (transaction.type == TransactionType.EXPENSE) Color(0xFFBF2F1A) else Color(
                    0xFF1F7601
                ), shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier.
                        weight(1f)
            ) {
                Text(
                    text = "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"} ${transaction.amount}",
                    style = MaterialTheme.typography.bodyLarge, fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
            }
            Column() {
                TagComponent(
                    text = transaction.category,
                    colorBackground = if (transaction.type == TransactionType.EXPENSE) Color(0xFF691307) else Color(
                        0xFF144603
                    ),
                    colorText = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Column() {
                Text(
                    text = "# ${transaction.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = dateFormat.format(Date(transaction.datetime)),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun TransactionComponentPreview() {
    FinControlTheme() {
        Column() {
            Row() {
                TransactionComponent(
                    transaction = Transaction(
                        id = 1,
                        amount = BigDecimal("500.00"),
                        type = TransactionType.EXPENSE,
                        category = TransactionCategories.ExpenseCategories.CLOTHES.name,
                        datetime = System.currentTimeMillis(),
                        profileId = 1
                    )
                )
            }
            Row() {
                TransactionComponent(
                    transaction = Transaction(
                        id = 2,
                        amount = BigDecimal("432432.00"),
                        type = TransactionType.EXPENSE,
                        category = TransactionCategories.ExpenseCategories.FOOD.name,
                        datetime = System.currentTimeMillis(),
                        profileId = 1
                    )
                )
            }
            Row() {
                TransactionComponent(
                    transaction = Transaction(
                        id = 2,
                        amount = BigDecimal("674564.00"),
                        type = TransactionType.INCOME,
                        category = TransactionCategories.IncomeCategories.INVESTS.name,
                        datetime = System.currentTimeMillis(),
                        profileId = 1
                    )
                )
            }
            Row() {

            }
        }


    }
}
