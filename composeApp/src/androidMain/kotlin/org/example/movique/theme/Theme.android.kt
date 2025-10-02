package org.example.movique.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration

@Composable
actual fun isSystemInDarkTheme(): Boolean {
	val uiMode = LocalConfiguration.current.uiMode
	return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}