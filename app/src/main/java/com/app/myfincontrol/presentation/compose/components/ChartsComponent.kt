package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.data.ChartSort
import com.example.compose.FinControlTheme
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.config.BarConfig
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.line.CurveLineChart
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.CurveLineConfig
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData

@Composable
fun ChartsComponent(
    data: List<Any>
) {

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (data.all { it is BarData }) {
                BarChart(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 16.dp)
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.5f),
                    color = MaterialTheme.colorScheme.primary,
                    chartDimens = ChartDimens(
                        padding = 16.dp,
                    ),
                    axisConfig = AxisConfig(
                        showAxis = true,
                        isAxisDashed = true,
                        showUnitLabels = true,
                        showXLabels = true,
                        xAxisColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        textColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                    barData = data.mapNotNull { it as? BarData },
                    onBarClick = { },
                    barConfig = BarConfig(
                        hasRoundedCorner = true
                    )
                )
            } else if(data.all { it is LineData }) {
                LineChart(
                    lineData = data.mapNotNull { it as? LineData },
                    color = MaterialTheme.colorScheme.primary,
                )
            } else if (data.all { it is PointData }) {
                PointChart(
                    pointData = data.mapNotNull { it as? PointData },
                    color = MaterialTheme.colorScheme.primary,
                    axisConfig = AxisConfig(
                        showAxis = true,
                        isAxisDashed = true,
                        showUnitLabels = true,
                        showXLabels = true,
                        xAxisColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        textColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Нет данных")
                }
            }
        }
    }
}

@Preview(device = "id:Nexus 5", showSystemUi = true, showBackground = true)
@Composable
fun FinancialChartsComponentPreview() {

    val chartSort = remember {
        mutableStateOf(ChartSort.YEAR)
    }

    val barDataList = listOf(
        BarData(1f, 245359f),
        BarData(2f, 234534f),
        BarData(4f, 153450f),
        BarData(5f, 155345f),
        BarData(6f, 205374f),
        BarData(7f, 255453f),
        BarData(8f, 304573f),
        BarData(9f, 255345f),
        BarData(10f, 103434f),
        BarData(11f, 155374f),
        BarData(12f, 104735f),
        )

    val dataPoints = remember {
        mutableListOf(
            PointData(1f, 2f),
            PointData(2f, 5f),
            PointData(4f, 10f),
            PointData(5f, 15f),
            PointData(6f, 20f),
        )
    }
    FinControlTheme() {
        ChartsComponent(barDataList)
    }
}