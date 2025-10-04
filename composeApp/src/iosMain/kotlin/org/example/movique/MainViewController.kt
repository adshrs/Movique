package org.example.movique

import androidx.compose.ui.window.ComposeUIViewController
import org.example.movique.di.initKoin
import platform.Foundation.NSLog

fun MainViewController() = ComposeUIViewController(
	configure = {
		NSLog("Initializing Koin")
		initKoin()
		NSLog("Koin initialized")
	}
) {
	MoviqueApp()
}