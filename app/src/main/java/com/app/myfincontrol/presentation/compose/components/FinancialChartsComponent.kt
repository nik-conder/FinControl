package com.app.myfincontrol.presentation.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
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
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.line.CurveLineChart
import com.himanshoe.charty.line.config.CurveLineConfig
import com.himanshoe.charty.line.model.LineData

@Composable
fun FinancialChartsComponent() {

    val showChart = remember { mutableStateOf(false) }

    val dataPoints = remember {
        mutableListOf<LineData>(
            LineData(1f,2000f),
            LineData(2f,4000f),
            LineData(3f,6000f),
            LineData(4f,1000f),
            LineData(5f,1500f),
            LineData(6f,2900f),
            LineData(7f,1200f),
            LineData(8f,4500f),
            LineData(9f,5900f),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp,  end = 16.dp)
    ) {

            Column {
                Row {
                    HeaderComponent(title = "График доходов")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column() {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_show_chart_24), contentDescription = "Chart")
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = "График дохода за последний месяц",
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Column() {
                                IconButton(onClick = {
                                    showChart.value = !showChart.value
                                }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (showChart.value) R.drawable.ic_baseline_expand_less_24 else R.drawable.ic_baseline_expand_more_24),
                                        contentDescription = "Advice"
                                    )
                                }
                            }
                        }

                        AnimatedVisibility(visible = showChart.value) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .height(150.dp)
                            ) {
                                if (dataPoints.size != 0) {
                                    CurveLineChart(
                                        modifier = Modifier
                                            .padding(top = 16.dp)
                                            .padding(start = 20.dp, end = 0.dp),
                                        chartColors = listOf(
                                            MaterialTheme.colorScheme.onTertiaryContainer,
                                            Color(red = 0, green = 188, blue = 212, alpha = 200)

                                        ),
                                        lineColors = listOf(
                                            MaterialTheme.colorScheme.primary,
                                            Color(red = 0, green = 188, blue = 212, alpha = 255)
                                        ),
                                        lineData = dataPoints,
                                        curveLineConfig = CurveLineConfig(hasDotMarker = false, Color.Red),
                                        axisConfig = AxisConfig(
                                            showAxis = true,
                                            isAxisDashed = true,
                                            showUnitLabels = true,
                                            showXLabels = false,
                                            xAxisColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                            yAxisColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                            textColor = MaterialTheme.colorScheme.onTertiaryContainer
                                        )
                                    )
                                } else {
                                    Text(text = "Нет данных (${dataPoints.size})")
                                }
                            }

                            LazyRow(
                                modifier = Modifier
                                    .padding(top = 180.dp), // TODO: костыль: искуственный отступ
                                contentPadding = PaddingValues(vertical = 8.dp)
                            ) {
                                item {
                                    TextButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Всё время")
                                    }
                                }
                                item {
                                    TextButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Неделя")
                                    }
                                }
                                item {
                                    TextButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Месяц")
                                    }
                                }
                                item {
                                    TextButton(onClick = { /*TODO*/ }) {
                                        Text(text = "Год")
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }
}

@Preview(device = "id:Nexus 5", showSystemUi = true, showBackground = true)
@Composable
fun FinancialChartsComponentPreview() {

    FinancialChartsComponent()
}