package org.example.movique.theme.thememode

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
	/** Flow that emits the selected mode (LIGHT, DARK, SYSTEM) */
	fun observeTheme(): Flow<ThemeMode>

	/** Persist a new selection */
	suspend fun setTheme(mode: ThemeMode)

	/** One-shot read */
	suspend fun getTheme(): ThemeMode
}