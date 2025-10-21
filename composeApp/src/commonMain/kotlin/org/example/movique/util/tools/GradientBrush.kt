package org.example.movique.util.tools

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// helper
private fun lerp(start: Float, end: Float, fraction: Float): Float =
	start + (end - start) * fraction

@Composable
fun smoothCinematicVerticalGradientBrush(
	baseColor: Color = MaterialTheme.colorScheme.background,
	topAlpha: Float = 0.2f,
	midAlpha: Float = 0.05f,
	bottomAlpha: Float = 1f,
	midPosition: Float = 0.3f,
	steps: Int = 120
): Brush {
	val colors = buildList {
		for (i in 0 until steps) {
			val t = i / (steps - 1f)

			// Smooth curve across entire gradient (easeInOut-like)
			val smoothT = t * t * (3 - 2 * t) // Smoothstep for no sharp rate changes

			// Apply a valley at the midpoint (for that cinematic “depth”)
			val curve = when {
				t < midPosition -> {
					val localT = t / midPosition
					lerp(topAlpha, midAlpha, localT * localT * (3 - 2 * localT))
				}

				else -> {
					val localT = (t - midPosition) / (1 - midPosition)
					lerp(midAlpha, bottomAlpha, localT * localT * (3 - 2 * localT))
				}
			}

			add(baseColor.copy(alpha = curve))
		}

		// Ensure solid bottom
		add(baseColor.copy(alpha = bottomAlpha))
	}

	return Brush.verticalGradient(
		colors = colors,
		startY = 0f,
		endY = Float.POSITIVE_INFINITY
	)
}