package com.app.myfincontrol.presentation.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.myfincontrol.data.enums.ChartSort
import com.example.compose.FinControlTheme
import com.himanshoe.charty.bar.BarChart
import com.himanshoe.charty.bar.config.BarConfig
import com.himanshoe.charty.bar.model.BarData
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.common.dimens.ChartDimens
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.model.LineData
import com.himanshoe.charty.point.PointChart
import com.himanshoe.charty.point.model.PointData

@Composable
fun ChartsComponent(
    data: List<Any>
) {
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (data.all { it is BarData }) {
            BarChart(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.9f),
                color = MaterialTheme.colorScheme.primary,
                chartDimens = ChartDimens(
                    padding = 26.dp,
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