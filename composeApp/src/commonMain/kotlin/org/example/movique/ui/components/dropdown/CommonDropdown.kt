package org.example.movique.ui.components.dropdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun <T> CommonDropdown(
	items: List<T>,
	selectedItem: T,
	onItemSelected: (T) -> Unit,
	labelMapper: (T) -> String,
	modifier: Modifier = Modifier,
	shape: Shape = RoundedCornerShape(12.dp)
) {
	var expanded by remember { mutableStateOf(false) }
	val textMeasurer = rememberTextMeasurer()

	// Find longest label
	val longestTextWidth = items.maxOfOrNull {
		textMeasurer.measure(
			labelMapper(it),
			style = MaterialTheme.typography.bodyMedium
		).size.width
	} ?: 0

	val density = LocalDensity.current
	val measuredTotalWidth = with(density) { longestTextWidth.toDp() + 48.dp }

	Box(modifier = modifier) {
		Card(
			onClick = { expanded = true },
			modifier = Modifier.width(measuredTotalWidth),
			shape = shape
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 6.dp)
					.padding(start = 12.dp, end = 4.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = labelMapper(selectedItem),
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurface,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Icon(
					imageVector = Icons.Default.ArrowDropDown,
					contentDescription = "Expand"
				)
			}
		}

		DropdownMenu(
			modifier = Modifier.width(measuredTotalWidth),
			expanded = expanded,
			onDismissRequest = { expanded = false },
			shape = shape
		) {
			items.forEach { item ->
				DropdownMenuItem(
					text = { Text(labelMapper(item)) },
					onClick = {
						expanded = false
						onItemSelected(item)
					}
				)
			}
		}
	}
}