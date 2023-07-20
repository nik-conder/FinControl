package com.app.myfincontrol.presentation.compose.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import java.util.Date

enum class TypeEvent {
    NOTIFICATION, TRANSACTION
}

@Composable
fun TransactionComponent(
    transaction: Transaction,
    hideBalanceState: Boolean,
    debugModeState: Boolean
) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(
                width = 2.dp,
                color = if (transaction.type == TransactionType.EXPENSE) Color(0xFFBF2F1A) else Color(
                    0xFF1F7601
                ),
                shape = RoundedCornerShape(20.dp)
            )
            /*
                border = if (selectedTransaction.value == TransactionType.INCOME) BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                ) else BorderStroke(
                                    width = 0.dp,
                                    color = Color.Transparent
                                ),
                 */
//            .background(
//                color = if (transaction.type == TransactionType.EXPENSE) Color(0xFFBF2F1A) else Color(
//                    0xFF1F7601
//                ), shape = RoundedCornerShape(20.dp)
//            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
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
                    text = if (hideBalanceState)
                        "\uD83E\uDD11 \uD83E\uDD11 \uD83E\uDD11"
                    else
                        "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"} ${transaction.amount}",
                    style = MaterialTheme.typography.bodyLarge, fontSize = 24.sp,
                    color = if (transaction.type == TransactionType.EXPENSE) Color(0xFF991E0D) else Color(
                        0xFF288B06
                    ),
                    fontWeight = FontWeight(700)
                )
            }
            Column() {

                TagComponent(
                    text = stringResource(
                        id = categoriesTranscript(
                            category =
                            if (transaction.type == TransactionType.EXPENSE)
                                TransactionCategories.ExpenseCategories.valueOf(transaction.category)
                            else
                                TransactionCategories.IncomeCategories.valueOf(transaction.category)
                        )
                    ),
                    colorBackground = if (transaction.type == TransactionType.EXPENSE) Color(0xFFB11D09) else Color(0xFF288B06),
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
            if (debugModeState) {
                Column() {
                    Text(
                        text = "# ${transaction.id}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = dateFormat.format(Date(transaction.datetime * 1000)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}