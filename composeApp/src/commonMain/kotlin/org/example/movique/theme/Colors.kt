package org.example.movique.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

fun moviqueLightColorScheme(): ColorScheme = lightColorScheme(
	primary = hexToColor("#1C252C"), // Deep cinematic navy
	onPrimary = hexToColor("#FFFFFF"), // White
	primaryContainer = hexToColor("#39414A"), // Muted dark blue-gray
	onPrimaryContainer = hexToColor("#E2E8F0"), // Light gray-blue

	inversePrimary = hexToColor("#D32F2F"), // Muted red for highlights

	secondary = hexToColor("#A38C21"), // Subtle gold
	onSecondary = hexToColor("#1A1500"), // Near-black
	secondaryContainer = hexToColor("#E6D98C"), // Soft golden beige
	onSecondaryContainer = hexToColor("#2C2400"), // Dark gold

	tertiary = hexToColor("#3F6E6A"), // Muted teal
	onTertiary = hexToColor("#FFFFFF"), // White
	tertiaryContainer = hexToColor("#B7D4D1"), // Soft pastel teal
	onTertiaryContainer = hexToColor("#1B3634"), // Deep teal

	background = hexToColor("#FAFAFA"), // Light gray
	onBackground = hexToColor("#1C252C"), // Deep gray-navy
	surface = hexToColor("#FFFFFF"), // Clean white
	onSurface = hexToColor("#1C252C"), // Deep gray-navy

	surfaceVariant = hexToColor("#E0E0E0"), // Neutral gray
	onSurfaceVariant = hexToColor("#4A4F55"), // Dark gray

	surfaceTint = hexToColor("#1C252C"), // Tint with primary
	inverseSurface = hexToColor("#2C3338"), // Darker gray
	inverseOnSurface = hexToColor("#E0E0E0"), // Light gray

	error = hexToColor("#B3261E"), // Muted cinematic red
	onError = hexToColor("#FFFFFF"), // White
	errorContainer = hexToColor("#F2B8B5"), // Soft pink-red
	onErrorContainer = hexToColor("#410002"), // Dark red

	outline = hexToColor("#757575"), // Medium gray
	outlineVariant = hexToColor("#B0BEC5"), // Blue-gray

	scrim = hexToColor("#000000"), // Black overlay

	surfaceBright = hexToColor("#FFFFFF"),
	surfaceDim = hexToColor("#ECECEC"),
	surfaceContainer = hexToColor("#F4F4F4"),
	surfaceContainerHigh = hexToColor("#EEEEEE"),
	surfaceContainerHighest = hexToColor("#E6E6E6"),
	surfaceContainerLow = hexToColor("#FAFAFA"),
	surfaceContainerLowest = hexToColor("#FFFFFF"),
)


fun moviqueDarkColorScheme(): ColorScheme = darkColorScheme(
	primary = hexToColor("#90A4AE"), // Muted steel blue
	onPrimary = hexToColor("#1C252C"), // Deep navy
	primaryContainer = hexToColor("#39414A"), // Charcoal blue
	onPrimaryContainer = hexToColor("#E2E8F0"), // Light gray-blue

	inversePrimary = hexToColor("#D32F2F"), // Muted red highlight

	secondary = hexToColor("#C5AC3D"), // Antique gold
	onSecondary = hexToColor("#1C1C00"), // Deep near-black
	secondaryContainer = hexToColor("#6B5E16"), // Earthy gold
	onSecondaryContainer = hexToColor("#F8ECB0"), // Pale gold

	tertiary = hexToColor("#6FA5A0"), // Soft teal
	onTertiary = hexToColor("#0B2423"), // Deep teal
	tertiaryContainer = hexToColor("#2D4A48"), // Dark teal container
	onTertiaryContainer = hexToColor("#DDEDEA"), // Soft pastel teal

	background = hexToColor("#121212"), // Cinematic deep black
	onBackground = hexToColor("#E0E0E0"), // Light gray
	surface = hexToColor("#1C252C"), // Navy-gray surface
	onSurface = hexToColor("#E0E0E0"), // Light gray

	surfaceVariant = hexToColor("#2E3B42"), // Muted steel gray
	onSurfaceVariant = hexToColor("#A7B1B7"), // Soft gray-blue

	surfaceTint = hexToColor("#90A4AE"),
	inverseSurface = hexToColor("#E0E0E0"),
	inverseOnSurface = hexToColor("#2C3338"),

	error = hexToColor("#CF6679"), // Soft muted red
	onError = hexToColor("#1C0A0A"),
	errorContainer = hexToColor("#8C1D18"),
	onErrorContainer = hexToColor("#FFDAD4"),

	outline = hexToColor("#78909C"), // Light steel-gray
	outlineVariant = hexToColor("#546E7A"), // Darker steel-gray

	scrim = hexToColor("#000000"),

	surfaceBright = hexToColor("#2A2E32"),
	surfaceDim = hexToColor("#14191A"),
	surfaceContainer = hexToColor("#1E2327"),
	surfaceContainerHigh = hexToColor("#252B30"),
	surfaceContainerHighest = hexToColor("#2E343A"),
	surfaceContainerLow = hexToColor("#181C20"),
	surfaceContainerLowest = hexToColor("#0E1114"),
)

fun hexToColor(hex: String): Color {
	val cleanHex = hex.removePrefix("#")
	require(cleanHex.length in 6..8) { "Invalid hex color: $hex" }
	val colorLong = cleanHex.toLong(16)
	return if (cleanHex.length == 8) {
		Color(
			red = ((colorLong shr 16) and 0xFF).toInt(),
			green = ((colorLong shr 8) and 0xFF).toInt(),
			blue = (colorLong and 0xFF).toInt(),
			alpha = ((colorLong shr 24) and 0xFF).toInt()
		)
	} else {
		Color(((colorLong shr 16) and 0xFF).toInt(), ((colorLong shr 8) and 0xFF).toInt(), (colorLong and 0xFF).toInt())
	}
}