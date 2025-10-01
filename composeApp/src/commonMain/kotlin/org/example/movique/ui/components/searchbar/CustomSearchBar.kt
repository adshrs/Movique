package org.example.movique.ui.components.searchbar

import androidx.compose.foundation.BasicTooltipDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CustomSearchBar(
	query: TextFieldValue,
	onQueryChange: (TextFieldValue) -> Unit,
	placeholder: String,
	modifier: Modifier = Modifier
) {
	var isFocused by remember { mutableStateOf(false) }
	val focusRequester = remember { FocusRequester() }

	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(52.dp)
			.clip(RoundedCornerShape(28.dp))
			.background(MaterialTheme.colorScheme.surfaceContainer)
			.then(
				if (isFocused) {
					Modifier.border(
						width = 1.5.dp,
						color = MaterialTheme.colorScheme.outlineVariant,
						shape = RoundedCornerShape(28.dp)
					)
				} else {
					Modifier.border(
						width = 0.5.dp,
						color = MaterialTheme.colorScheme.outlineVariant,
						shape = RoundedCornerShape(28.dp)
					)
				}
			),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		Icon(
			imageVector = Icons.Default.Search,
			contentDescription = "Search Icon",
			modifier = Modifier
				.padding(start = 16.dp)
				.size(24.dp),
			tint = if (isFocused) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
		)
		BasicTextField(
			value = query,
			onValueChange = onQueryChange,
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 12.dp)
				.focusRequester(focusRequester)
				.onFocusChanged { focusState ->
					isFocused = focusState.isFocused
				},
			textStyle = MaterialTheme.typography.bodyLarge.copy(
				color = MaterialTheme.colorScheme.onSurface
			),
			cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
			decorationBox = { innerTextField ->
				if (query.text.isEmpty()) {
					Text(
						text = placeholder,
						style = MaterialTheme.typography.bodyLarge,
						color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
					)
				}
				innerTextField()
			}
		)
	}
}