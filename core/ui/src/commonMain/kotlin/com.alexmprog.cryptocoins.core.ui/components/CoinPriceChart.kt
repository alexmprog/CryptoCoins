package com.alexmprog.cryptocoins.core.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp

@Composable
fun CoinPriceChart(modifier: Modifier, data: List<Pair<Int, Double>>) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val highValue = remember(key1 = data) { (data.maxOfOrNull { it.second }?.toFloat()) ?: 0f }
    val lowValue = remember(key1 = data) { (data.minOfOrNull { it.second }?.toFloat()) ?: 0f }
    val range = remember(key1 = highValue, key2 = lowValue) { highValue - lowValue }
    val animationProgress = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = data) {
        animationProgress.animateTo(1f, tween(1500))
    }
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val spacePerItem = width / data.size
            var avgX: Float
            var avgY: Float
            val strokePath = Path().apply {
                data.indices.forEach { i ->
                    val nextData = data.getOrNull(i + 1) ?: data.last()
                    val ratio = (data[i].second.toFloat() - lowValue) / range
                    val nextRatio = (nextData.second.toFloat() - lowValue) / range
                    val x1 = i * spacePerItem
                    val y1 = height - (ratio * height)
                    val x2 = (i + 1) * spacePerItem
                    val y2 = height - (nextRatio * height)
                    if (i == 0) {
                        moveTo(x1, y1)
                    } else {
                        avgX = (x1 + x2) / 2f
                        avgY = (y1 + y2) / 2f
                        quadraticBezierTo(x1 = x1, y1 = y1, x2 = avgX, y2 = avgY)
                    }
                }
            }

            val fillPath = Path().apply {
                addPath(strokePath)
                lineTo(width - spacePerItem, height)
                lineTo(0f, height)
                close()
            }

            clipRect(right = size.width * animationProgress.value) {
                drawPath(
                    path = strokePath,
                    color = primaryColor,
                    style = Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round),
                )

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(secondaryColor, Color.Transparent),
                        endY = height,
                    ),
                )
            }
        }
    }
}