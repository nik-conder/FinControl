package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.data.TransactionCategories
import com.app.myfincontrol.data.TransactionType
import com.app.myfincontrol.data.entities.Transaction

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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = if (transaction.type == TransactionType.EXPENSE) Color(0xFFBF2F1A) else Color(0xFF1F7601), shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"} ${transaction.amount}",
                style = MaterialTheme.typography.bodyLarge, fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight(700)
            )
        }
        Row {
            Text(text = "${transaction.datetime}", style = MaterialTheme.typography.bodySmall)
        }
    }
}