package org.example.movique.ui.components.badge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import org.example.movique.util.tools.Constants.NA

@Composable
fun MediaTypeBadge(
	modifier: Modifier = Modifier,
	mediaType: String?,
	textStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
	Badge(
		modifier = modifier,
		containerColor =
			if (mediaType == "movie")
				MaterialTheme.colorScheme.primaryContainer.copy(0.9f)
			else
				MaterialTheme.colorScheme.tertiary.copy(0.9f),
		contentColor =
			if (mediaType == "movie")
				Color.White.copy(0.9f)
			else
				Color.White.copy(0.9f)
	) {
		Text(
			text = when (mediaType) {
				"movie" -> "Movie"
				"tv" -> "TV Series"
				else -> NA
			},
			style = textStyle,
			modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
		)
	}
}