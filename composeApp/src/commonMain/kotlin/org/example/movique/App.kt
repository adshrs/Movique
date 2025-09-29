package org.example.movique

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.movique.theme.MoviqueTheme
import org.example.movique.ui.ThemeTestScreen

@Composable
@Preview
fun App() {
	MoviqueTheme {
		ThemeTestScreen()
	}
}