package org.example.movique.ui.components.tab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoRippleTab(
	modifier: Modifier = Modifier,
	title: String,
	count: Int? = null,
	index: Int,
	selectedTabIndex: Int,
	onClick: (Int) -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.selectable(
				selected = selectedTabIndex == index,
				onClick = { onClick(index) },
				interactionSource = remember { MutableInteractionSource() },
				indication = null
			),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Row(
			modifier = Modifier,
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center
		) {
			Text(
				modifier = Modifier.padding(top = 8.dp, bottom = 10.dp),
				text = title,
				style = MaterialTheme.typography.titleSmall,
				textAlign = TextAlign.Center,
			)
			count?.let {
				if (it > 0) {
					Spacer(modifier = Modifier.width(4.dp))
					Badge(
						containerColor = MaterialTheme.colorScheme.primary,
						contentColor = MaterialTheme.colorScheme.onPrimary
					) {
						Text(
							text = if (count < 1000) count.toString() else "999+",
							style = MaterialTheme.typography.labelSmall
						)
					}
				}
			}
		}
	}
}