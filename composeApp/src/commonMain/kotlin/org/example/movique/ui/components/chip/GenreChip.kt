package org.example.movique.ui.components.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenreChip(text: String) {
	Box(
		modifier = Modifier
			.background(
				color = MaterialTheme.colorScheme.surfaceContainer.copy(0.9f),
				shape = RoundedCornerShape(50)
			)
			.border(
				border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary.copy(0.8f)),
				shape = MaterialTheme.shapes.small
			)
			.padding(horizontal = 8.dp, vertical = 4.dp)
	) {
		Text(
			text = text,
			style = MaterialTheme.typography.labelSmall,
			color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
		)
	}
}