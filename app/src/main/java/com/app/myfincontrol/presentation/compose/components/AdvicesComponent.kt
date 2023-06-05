package com.app.myfincontrol.presentation.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.R


data class Advice(
    val title: Int,
    val description: Int
)

@Composable
fun AdvicesComponent() {

    val showAdvice = remember { mutableStateOf(false) }
    val adviceList = listOf(
        Advice(title = R.string.advice_title_1, description = R.string.advice_description_1),
        Advice(title = R.string.advice_title_2, description = R.string.advice_description_2),
        Advice(title = R.string.advice_title_3, description = R.string.advice_description_3),
        Advice(title = R.string.advice_title_4, description = R.string.advice_description_4),
        Advice(title = R.string.advice_title_5, description = R.string.advice_description_5)
       )
    val randomIndexAdvice = remember { mutableStateOf(0) }

    LaunchedEffect(true) {
        randomIndexAdvice.value = (0..4).random()
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Column() {
            Row() {
                HeaderComponent(title = "Полезные советы")
            }
            Row {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .wrapContentHeight()
                        .padding(16.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column() {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_tips_and_updates_24), contentDescription = "Advice")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = adviceList[randomIndexAdvice.value].title),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                            Column() {
                                IconButton(onClick = {
                                    showAdvice.value = !showAdvice.value
                                }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (showAdvice.value) R.drawable.ic_baseline_expand_less_24 else R.drawable.ic_baseline_expand_more_24),
                                        contentDescription = "Advice"
                                    )
                                }
                            }
                        }
                        AnimatedVisibility(visible = showAdvice.value) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = adviceList[randomIndexAdvice.value].description),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun AdvicesComponentPreview() {
    AdvicesComponent()
}