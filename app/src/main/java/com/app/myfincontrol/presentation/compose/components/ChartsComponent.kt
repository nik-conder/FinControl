package com.app.myfincontrol.presentation.compose.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FinControlTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
fun ChartsComponent(
    data: List<FloatEntry>
) {

    val lineComponent = LineComponent(
        color = MaterialTheme.colorScheme.primary.toArgb(),
        strokeColor = android.graphics.Color.BLACK
    )

    // удалён remember

    ProvideChartStyle(ChartStyle(
        axis = ChartStyle.Axis(
            axisLabelColor = MaterialTheme.colorScheme.secondary,
            axisGuidelineColor = MaterialTheme.colorScheme.secondary,
            axisLineColor = MaterialTheme.colorScheme.secondary
        ),
        columnChart = ChartStyle.ColumnChart(
            columns = listOf(lineComponent)
        ),
        lineChart = ChartStyle.LineChart(
            lines = emptyList(),
        ),
        marker = ChartStyle.Marker(

        ),
        elevationOverlayColor = Color.Red
    )
    ) {
        Chart(
            chart = columnChart(),
            isZoomEnabled = true,
            model = entryModelOf(data),
            startAxis = startAxis(tickLength = 1.dp, maxLabelCount = 5),
            bottomAxis = bottomAxis(tickLength = 1.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ChartsComponentPreview() {
   FinControlTheme() {
       ChartsComponent(data = listOf(
           FloatEntry(10f, 10f),
           FloatEntry(20f, 30f),
           FloatEntry(30f, 40f),
       )
       )
   }
}