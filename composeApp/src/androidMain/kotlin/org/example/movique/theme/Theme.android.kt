package org.example.movique.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import org.example.movique.theme.thememode.ThemeMode

@Composable
actual fun isSystemInDarkTheme(): Boolean {
	val uiMode = LocalConfiguration.current.uiMode
	return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}

@Composable
actual fun SetSystemBarsColor(
	statusBarColor: Color,
	navigationBarColor: Color,
	darkIcons: Boolean
) {
	val view = LocalView.current
	if (!view.isInEditMode) {
		SideEffect {
			val window = (view.context as Activity).window

			// Apply colors
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				window.statusBarColor = statusBarColor.toArgb()
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				window.navigationBarColor = navigationBarColor.toArgb()
			}

			// Control icon contrast
			val controller = WindowInsetsControllerCompat(window, view)
			controller.isAppearanceLightStatusBars = darkIcons
			controller.isAppearanceLightNavigationBars = darkIcons
		}
	}
}