@file:OptIn(ExperimentalSettingsApi::class)

package org.example.movique.theme.thememode

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringFlow
import kotlinx.coroutines.flow.map

class ThemeRepositoryImpl(private val settings: ObservableSettings) : ThemeRepository {
	private val KEY = "pref_theme_mode"

	override fun observeTheme() =
		settings.getStringFlow(KEY, ThemeMode.SYSTEM.name)
			.map { raw ->
				ThemeMode.entries.firstOrNull { it.name == raw } ?: ThemeMode.SYSTEM
			}

	override suspend fun setTheme(mode: ThemeMode) {
		settings.putString(KEY, mode.name)
	}

	override suspend fun getTheme(): ThemeMode {
		val raw = settings.getStringOrNull(KEY)
		return ThemeMode.entries.firstOrNull { it.name == raw } ?: ThemeMode.SYSTEM
	}
}
