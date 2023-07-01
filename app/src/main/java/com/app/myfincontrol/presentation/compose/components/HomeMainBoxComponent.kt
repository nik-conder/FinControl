package com.app.myfincontrol.presentation.compose.components

import java.math.BigDecimal
import androidx.compose.foundation.background
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
import androidx.compose.material3.rememberPlainTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.enums.Currency
import com.app.myfincontrol.presentation.viewModels.events.TransactionEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainBoxComponent(
    hideBalance: Boolean,
    profileName: String,
    balance: BigDecimal,
    currency: Currency,
    onEventsTransaction: (TransactionEvents) -> Unit,
) {
    val tooltipState = rememberPlainTooltipState()
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
                        text = if (hideBalance) "\uD83E\uDD11 \uD83E\uDD11 \uD83E\uDD11" else "$balance ${currencySymbolComponent(currency)}",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 42.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight(500)
                    )
                }
                Column() {
                    PlainTooltipBox(
                        tooltip = {
                            Text(text = "Вы можете скрыть баланс")
                                  },
                        tooltipState = tooltipState,
                        shape = RoundedCornerShape(20.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ) {
                        IconButton(onClick = {
                            onEventsTransaction(TransactionEvents.HideBalance)
                        }) {
                            Icon(
                                painter = painterResource(id = if (hideBalance) R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24),
                                contentDescription = stringResource(id = R.string.visibility_content_description),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
            Row {
                Text(
                    text = profileName,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}