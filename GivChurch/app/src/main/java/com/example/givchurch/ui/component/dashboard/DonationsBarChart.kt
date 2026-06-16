package com.example.givchurch.ui.component.dashboard

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.givchurch.domain.model.MonthlyDonation

@Composable
fun DonationsBarChart(data: List<MonthlyDonation>) {
    val barColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant.toArgb()
    val maxVal = (data.maxOfOrNull { it.totalAmount } ?: 0).coerceAtLeast(80)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val axisOffsetLeft = 80f
        val axisOffsetBottom = 60f
        val chartWidth = canvasWidth - axisOffsetLeft
        val chartHeight = canvasHeight - axisOffsetBottom
        drawLine(
            color = Color.Gray.copy(alpha = 0.5f),
            start = Offset(axisOffsetLeft, 0f),
            end = Offset(axisOffsetLeft, chartHeight),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Gray.copy(alpha = 0.5f),
            start = Offset(axisOffsetLeft, chartHeight),
            end = Offset(canvasWidth, chartHeight),
            strokeWidth = 2f
        )
        val ySteps = 4
        for (i in 0..ySteps) {
            val yValue = (maxVal / ySteps) * i
            val yPos = chartHeight - (chartHeight / ySteps) * i
            drawContext.canvas.nativeCanvas.drawText(
                yValue.toString(),
                axisOffsetLeft - 20f,
                yPos + 10f,
                Paint().apply {
                    color = textColor
                    textSize = 32f
                    textAlign = Paint.Align.RIGHT
                }
            )
        }
        if (data.isNotEmpty()) {
            val barWidth = (chartWidth / data.size) * 0.6f
            val spaceWidth = (chartWidth / data.size) * 0.4f
            data.forEachIndexed { index, item ->
                val barHeight = if (maxVal > 0) (item.totalAmount.toFloat() / maxVal) * chartHeight else 0f
                val xPos = axisOffsetLeft + (index * (barWidth + spaceWidth)) + (spaceWidth / 2)
                val yPos = chartHeight - barHeight
                drawRoundRect(
                    color = barColor,
                    topLeft = Offset(xPos, yPos),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(12f, 12f)
                )
                drawContext.canvas.nativeCanvas.drawText(
                    item.monthName,
                    xPos + (barWidth / 2),
                    chartHeight + 40f,
                    Paint().apply {
                        color = textColor
                        textSize = 32f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}