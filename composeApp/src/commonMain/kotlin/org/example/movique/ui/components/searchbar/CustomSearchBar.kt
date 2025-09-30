package org.example.movique.ui.components.searchbar

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CustomSearchBar(
	query: TextFieldValue,
	onQueryChange: (TextFieldValue) -> Unit,
	placeholder: String,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.height(52.dp)
			.clip(RoundedCornerShape(28.dp))
			.background(MaterialTheme.colorScheme.surface)
			.border(
				width = 1.dp,
				color = MaterialTheme.colorScheme.outlineVariant,
				shape = RoundedCornerShape(28.dp)
			)
			.shadow(4.dp, RoundedCornerShape(28.dp)),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		Icon(
			imageVector = Icons.Default.Search,
			contentDescription = "Search Icon",
			modifier = Modifier
				.padding(start = 16.dp)
				.size(24.dp),
			tint = MaterialTheme.colorScheme.onSurfaceVariant
		)
		BasicTextField(
			value = query,
			onValueChange = onQueryChange,
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 12.dp),
			textStyle = MaterialTheme.typography.bodyLarge.copy(
				color = MaterialTheme.colorScheme.onSurface
			),
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