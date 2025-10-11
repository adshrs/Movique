package org.example.movique.ui.components.cardshimmer

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoadingRow() {
	val shimmerColors = listOf(
		Color.Gray.copy(alpha = 0.6f),
		Color.Gray.copy(alpha = 0.2f),
		Color.Gray.copy(alpha = 0.6f)
	)
	val transition = rememberInfiniteTransition()
	val translateAnim by transition.animateFloat(
		initialValue = 0f,
		targetValue = 1000f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 1200,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Restart
		)
	)

	LazyRow(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(horizontal = 16.dp)
	) {
		items(5) {
			ShimmerCard(
				modifier = Modifier
					.width(128.dp)
					.padding(end = 8.dp),
				translateAnim = translateAnim,
				shimmerColors = shimmerColors
			)
		}
	}
}

@Composable
fun ShimmerCard(
	modifier: Modifier = Modifier,
	translateAnim: Float = 0f,
	shimmerColors: List<Color> = listOf(
		Color(0xFFE0E0E0).copy(alpha = 0.3f), // Subtle light gray
		Color(0xFFE0E0E0).copy(alpha = 0.1f), // Softer highlight
		Color(0xFFE0E0E0).copy(alpha = 0.3f)  // Back to base
	)
) {
	// Pulse animation for subtle scaling effect
	val pulseAnim by rememberInfiniteTransition().animateFloat(
		initialValue = 0.99f,
		targetValue = 1.01f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 800,
				easing = EaseInOutQuad
			),
			repeatMode = RepeatMode.Reverse
		)
	)

	// Gradient brush for shimmer effect
	val brush = Brush.linearGradient(
		colors = shimmerColors,
		start = Offset(translateAnim - 300f, 0f), // Wider gradient for smoother effect
		end = Offset(translateAnim, 0f)
	)

	OutlinedCard(
		modifier = modifier
			.width(120.dp) // Exact width as MovieCard
		, // Subtle pulse effect
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainer,
			contentColor = MaterialTheme.colorScheme.onSurface
		),
		border = BorderStroke(
			0.5.dp,
			MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
		) // Same border as MovieCard
	) {
		Column {
			// Poster placeholder (matches MovieCard's aspect ratio)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(2f / 3f) // Exact 2:3 ratio as MovieCard
					.background(brush)
					.clip(MaterialTheme.shapes.medium) // Rounded corners for polish
			)

			// Text area (matches MovieCard's padding and spacing)
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 8.dp, vertical = 8.dp), // Exact padding as MovieCard
				verticalArrangement = Arrangement.spacedBy(10.dp) // Exact spacing as MovieCard
			) {
				// Title placeholder (matches title text size and height)
				Column {
					Box(
						modifier = Modifier
							.fillMaxWidth() // Slightly shorter to mimic title variation
							.height(16.dp) // Approximates titleSmall with 2 lines
							.background(brush, RoundedCornerShape(8.dp))

					)
					Spacer(modifier = Modifier.height(4.dp))
					Box(
						modifier = Modifier
							.fillMaxWidth() // Slightly shorter to mimic title variation
							.height(16.dp) // Approximates titleSmall with 2 lines
							.background(brush, RoundedCornerShape(8.dp))
					)
				}
				// Rating placeholder (matches rating row size)
				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Row {
						Box(
							modifier = Modifier
								.size(14.dp) // Matches Star icon size
								.background(brush, RoundedCornerShape(8.dp))

						)
						Spacer(modifier = Modifier.width(4.dp)) // Mimic icon-text gap
						Box(
							modifier = Modifier
								.width(32.dp) // Approximates rating text width
								.height(14.dp) // Matches bodySmall
								.background(brush, RoundedCornerShape(8.dp))
						)
					}
					Box(
						modifier = Modifier
							.width(40.dp) // Approximates rating text width
							.height(14.dp) // Matches bodySmall
							.background(brush, RoundedCornerShape(8.dp))
					)
				}
			}
		}
	}
}