package org.example.movique.ui.components.divider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DottedDivider(
	modifier: Modifier = Modifier,
	thickness: Dp = 1.dp,
	color: Color = DividerDefaults.color,
	dashLength: Float = 4f,     // length of dot/dash
	gapLength: Float = 4f,      // space between dots/dashes
	phase: Float = 0f           // optional starting offset
) {
	Canvas(
		modifier = modifier
			.fillMaxWidth()
			.height(thickness)
	) {
		drawLine(
			color = color,
			start = Offset(0f, size.height / 2),
			end = Offset(size.width, size.height / 2),
			strokeWidth = thickness.toPx(),
			pathEffect = dashPathEffect(
				intervals = floatArrayOf(dashLength, gapLength),
				phase = phase
			)
		)
	}
}