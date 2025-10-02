package org.example.movique.theme

import androidx.compose.runtime.Composable
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.currentTraitCollection

@Composable
actual fun isSystemInDarkTheme(): Boolean {
	return UITraitCollection.currentTraitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
}