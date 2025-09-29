package org.example.movique.theme

import androidx.compose.material3.LocalTonalElevationEnabled
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
fun MoviqueTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val colorScheme = if (darkTheme) moviqueDarkColorScheme() else moviqueLightColorScheme()
	val typography = moviqueTypography()

	MaterialTheme(
		colorScheme = colorScheme,
		typography = typography,
		content = content
	)
}

@Composable
expect fun isSystemInDarkTheme(): Boolean