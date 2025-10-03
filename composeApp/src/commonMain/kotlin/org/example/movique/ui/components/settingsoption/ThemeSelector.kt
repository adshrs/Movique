package org.example.movique.ui.components.settingsoption

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import movique.composeapp.generated.resources.Res
import movique.composeapp.generated.resources.ic_theme
import org.example.movique.theme.thememode.ThemeMode
import org.example.movique.theme.thememode.ThemeRepository
import org.example.movique.ui.components.dropdown.CommonDropdown
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun ThemeSelector() {
	val repo: ThemeRepository = koinInject()
	val themeFlow = remember { repo.observeTheme() }
	val currentTheme by themeFlow.collectAsState(initial = ThemeMode.SYSTEM)
	val scope = rememberCoroutineScope()

	val options = ThemeMode.entries
	val labelMapper: (ThemeMode) -> String = {
		when (it) {
			ThemeMode.LIGHT -> "Light"
			ThemeMode.DARK -> "Dark"
			ThemeMode.SYSTEM -> "System"
		}
	}

	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			painter = painterResource(Res.drawable.ic_theme),
			contentDescription = "Theme",
			modifier = Modifier.size(18.dp)
		)
		Spacer(modifier = Modifier.width(8.dp))
		Text(
			text = "Theme",
			modifier = Modifier.weight(1f),
			style = MaterialTheme.typography.bodyLarge,
			color = MaterialTheme.colorScheme.onSurface
		)

		CommonDropdown(
			items = options,
			selectedItem = currentTheme,
			onItemSelected = { mode -> scope.launch { repo.setTheme(mode) } },
			labelMapper = labelMapper
		)
	}
}