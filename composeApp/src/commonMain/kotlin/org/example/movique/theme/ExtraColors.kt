package org.example.movique.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtraColors(
	val brandRed: Color,
	val ratingGold: Color,
	val cardOverlay: Color,
	val successGreen: Color,
	val warningOrange: Color
)

val LightExtraColors = ExtraColors(
	brandRed = hexToColor("#D32F2F"),
	ratingGold = hexToColor("#EDC134"),
	cardOverlay = hexToColor("#F5F5F5"),
	successGreen = hexToColor("#2E7D32"),
	warningOrange = hexToColor("#ED6C02")
)

val DarkExtraColors = ExtraColors(
	brandRed = hexToColor("#EF9A9A"),
	ratingGold = hexToColor("#FFD54F"),
	cardOverlay = hexToColor("#2E2E2E"),
	successGreen = hexToColor("#81C784"),
	warningOrange = hexToColor("#FFB74D")
)

val LocalExtraColors = staticCompositionLocalOf { LightExtraColors }

val MaterialTheme.extraColors: ExtraColors
	@Composable
	get() = LocalExtraColors.current