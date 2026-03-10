package org.example.movique.ui.components.badge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.example.movique.theme.extraColors
import org.example.movique.util.tools.Constants.NA

@Composable
fun MediaStatusBadge(
	modifier: Modifier = Modifier,
	mediaType: String?,
	status: String?,
	textStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
	if (!isUpcoming(mediaType, status)) {
		return  // Don't show anything
	}

	Badge(
		modifier = modifier,
		containerColor = MaterialTheme.extraColors.warningOrange.copy(alpha = 0.9f),
		contentColor = Color.White
	) {
		Text(
			text = "• UPCOMING",
			style = textStyle,
			modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
		)
	}
}

private fun isUpcoming(mediaType: String?, status: String?): Boolean {
	if (status.isNullOrBlank()) return false

	val type = mediaType?.lowercase() ?: return false
	val st = status.lowercase().trim()

	return when (type) {
		"movie" -> st in listOf("planned", "in production", "post production", "rumored")
		"tv", "tv series", "series" -> st in listOf("planned", "in production", "pilot")
		else -> false
	}
}