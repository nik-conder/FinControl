package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.enums.TransactionCategories
import com.app.myfincontrol.data.enums.TransactionType
import com.app.myfincontrol.presentation.utils.UtilsCompose
import java.math.BigDecimal

@Composable
fun TransactionComponent(
    transaction: Transaction,
    hideBalanceState: Boolean,
    debugModeState: Boolean
) {

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
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = if (hideBalanceState)
                        "\uD83E\uDD11 \uD83E\uDD11 \uD83E\uDD11"
                    else
                        "${if (transaction.type == TransactionType.EXPENSE) "-" else "+"} ${
                            UtilsCompose.Numbers.formatBigDecimalWithSpaces(
                                transaction.amount
                            )
                        }",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 22.sp,
                    color = if (transaction.type == TransactionType.EXPENSE) Color(0xFF991E0D) else Color(
                        0xFF288B06
                    ),
                    fontWeight = FontWeight(700)
                )
            }
            Column {
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
                    colorBackground = if (transaction.type == TransactionType.EXPENSE) Color(
                        0xFFB11D09
                    ) else Color(0xFF288B06),
                    colorText = Color.White
                )
            }

        }
        if (transaction.note != null) {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = transaction.note
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = UtilsCompose.Date.formatDate(transaction.datetime * 1000),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TransactionComponentPreview() {

    val tList = listOf(
        Transaction(
            id = 1,
            type = TransactionType.EXPENSE,
            profileId = 1,
            amount = BigDecimal(100),
            category = TransactionCategories.ExpenseCategories.TRANSPORTATION.name,
            datetime = System.currentTimeMillis(),
            note = "dskfjsdklfkljsdfhkjs"
        ),
        /*Transaction(
            id = 2,
            type = TransactionType.INCOME,
            profileId = 1,
            amount = BigDecimal(2053534),
            category = TransactionCategories.IncomeCategories.INVESTMENTS.name,
            datetime = System.currentTimeMillis(),
        )*/
    )
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = tList) {
            TransactionComponent(
                transaction = it,
                hideBalanceState = false,
                debugModeState = false
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TransactionComponentPreview2() {

    val tList = listOf(
        Transaction(
            id = 2,
            type = TransactionType.INCOME,
            profileId = 1,
            amount = BigDecimal(2053534),
            category = TransactionCategories.IncomeCategories.INVESTMENTS.name,
            datetime = System.currentTimeMillis(),
        )
    )
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = tList) {
            TransactionComponent(
                transaction = it,
                hideBalanceState = false,
                debugModeState = false
            )
        }
    }
}