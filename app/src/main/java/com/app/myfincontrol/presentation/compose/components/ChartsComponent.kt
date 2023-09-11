package com.app.myfincontrol.presentation.compose.components

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.myfincontrol.R
import com.app.myfincontrol.data.entities.InfoPageType
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChartsComponent(
    data: List<FloatEntry>?
) {
    val lineComponent = LineComponent(
        color = MaterialTheme.colorScheme.primary.toArgb(),
        thicknessDp = 10f,
        shape = Shapes.pillShape,
    )

    // удалён remember

    ProvideChartStyle(
        ChartStyle(
            axis = ChartStyle.Axis(
                axisLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                axisGuidelineColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                axisLineColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                axisLabelTextSize = 12.sp,
                axisLabelVerticalMargin = 2.dp,
                axisLabelHorizontalMargin = 2.dp,
                axisLabelVerticalPadding = 2.dp,
                axisLabelHorizontalPadding = 2.dp,
                axisLineShape = Shapes.pillShape,
                axisLineWidth = 1.dp,
                axisGuidelineShape = Shapes.pillShape,
                axisGuidelineWidth = 1.dp,
                axisTickColor = MaterialTheme.colorScheme.onSecondaryContainer,
                axisTickWidth = 1.dp,
                axisTickLength = 1.dp,
                axisLabelTextAlign = Paint.Align.CENTER

            ),
            columnChart = ChartStyle.ColumnChart(
                columns = listOf(lineComponent),
                mergeMode = ColumnChart.MergeMode.Stack,
                dataLabelVerticalPosition = VerticalPosition.Top,

                //dataLabel = TextComponent.Builder().build(),
            ),
            lineChart = ChartStyle.LineChart(
                lines = emptyList(),
                spacing = 1.dp
            ),
            marker = ChartStyle.Marker(
                indicatorSize = 1.dp,
                verticalPadding = 1.dp,
                horizontalPadding = 1.dp,
            ),
            elevationOverlayColor = Color.Red
        )
    ) {

        if (data != null) {
            if (data.isNotEmpty()) {
                Chart(
                    chart = columnChart(),
                    isZoomEnabled = true,
                    model = entryModelOf(
                        data
                    ),
                    startAxis = startAxis(
                        maxLabelCount = 8,
                    ),
                    bottomAxis = bottomAxis(tickLength = 1.dp),
                    marker = MarkerComponent(
                        label = TextComponent.Builder().build(),
                        indicator = null,
                        guideline = null
                    ),
                )
            } else {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    InfoPageComponent(InfoPageType.NO_DATA)
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.no_data),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}