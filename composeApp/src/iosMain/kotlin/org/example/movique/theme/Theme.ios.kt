package org.example.movique.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.currentTraitCollection

@Composable
actual fun isSystemInDarkTheme(): Boolean {
	return UITraitCollection.currentTraitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
}

@Composable
actual fun SetSystemBarsColor(
	statusBarColor: Color,
	navigationBarColor: Color,
	darkIcons: Boolean
) {
}