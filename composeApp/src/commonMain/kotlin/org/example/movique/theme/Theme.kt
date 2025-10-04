package org.example.movique.theme

import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.example.movique.theme.thememode.ThemeMode

@Composable
fun MoviqueTheme(themeMode: ThemeMode, content: @Composable () -> Unit) {
	val isDarkTheme = when (themeMode) {
		ThemeMode.LIGHT -> false
		ThemeMode.DARK  -> true
		ThemeMode.SYSTEM -> isSystemInDarkTheme()
	}
	val colorScheme = if (isDarkTheme) moviqueDarkColorScheme() else moviqueLightColorScheme()
	val extraColors = if (isDarkTheme) DarkExtraColors else LightExtraColors
	val typography = moviqueTypography()

	SetSystemBarsColor(
		statusBarColor = colorScheme.primary,
		navigationBarColor = colorScheme.background,
		darkIcons = !isDarkTheme
	)

	CompositionLocalProvider(
		LocalExtraColors provides extraColors
	) {
		MaterialTheme(
			colorScheme = colorScheme,
			typography = typography,
			content = content
		)
	}
}

@Composable
expect fun isSystemInDarkTheme(): Boolean

@Composable
expect fun SetSystemBarsColor(
	statusBarColor: Color,
	navigationBarColor: Color = statusBarColor,
	darkIcons: Boolean
)