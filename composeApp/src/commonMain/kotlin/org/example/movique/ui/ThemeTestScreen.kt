@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.example.movique.theme.moviqueDarkColorScheme
import org.example.movique.theme.moviqueLightColorScheme

@Composable
fun ThemeTestScreen() {
	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text("Movique Theme Tester") },
			)
		}
	) { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.verticalScroll(rememberScrollState())
					.padding(16.dp)
			) {
				// Color Showcase
				Text(
					text = "Color Scheme Showcase",
					style = typography.headlineLarge,
					modifier = Modifier.padding(bottom = 16.dp)
				)

				// Light Color Scheme
				Text(
					text = "Light Color Scheme",
					style = typography.headlineMedium,
					modifier = Modifier.padding(vertical = 8.dp)
				)
				ColorDisplay("Primary", moviqueLightColorScheme().primary)
				ColorDisplay("On Primary", moviqueLightColorScheme().onPrimary)
				ColorDisplay("Primary Container", moviqueLightColorScheme().primaryContainer)
				ColorDisplay("On Primary Container", moviqueLightColorScheme().onPrimaryContainer)
				ColorDisplay("Inverse Primary", moviqueLightColorScheme().inversePrimary)
				ColorDisplay("Secondary", moviqueLightColorScheme().secondary)
				ColorDisplay("On Secondary", moviqueLightColorScheme().onSecondary)
				ColorDisplay("Secondary Container", moviqueLightColorScheme().secondaryContainer)
				ColorDisplay("On Secondary Container", moviqueLightColorScheme().onSecondaryContainer)
				ColorDisplay("Tertiary", moviqueLightColorScheme().tertiary)
				ColorDisplay("On Tertiary", moviqueLightColorScheme().onTertiary)
				ColorDisplay("Tertiary Container", moviqueLightColorScheme().tertiaryContainer)
				ColorDisplay("On Tertiary Container", moviqueLightColorScheme().onTertiaryContainer)
				ColorDisplay("Background", moviqueLightColorScheme().background)
				ColorDisplay("On Background", moviqueLightColorScheme().onBackground)
				ColorDisplay("Surface", moviqueLightColorScheme().surface)
				ColorDisplay("On Surface", moviqueLightColorScheme().onSurface)
				ColorDisplay("Surface Variant", moviqueLightColorScheme().surfaceVariant)
				ColorDisplay("On Surface Variant", moviqueLightColorScheme().onSurfaceVariant)
				ColorDisplay("Surface Tint", moviqueLightColorScheme().surfaceTint)
				ColorDisplay("Inverse Surface", moviqueLightColorScheme().inverseSurface)
				ColorDisplay("Inverse On Surface", moviqueLightColorScheme().inverseOnSurface)
				ColorDisplay("Error", moviqueLightColorScheme().error)
				ColorDisplay("On Error", moviqueLightColorScheme().onError)
				ColorDisplay("Error Container", moviqueLightColorScheme().errorContainer)
				ColorDisplay("On Error Container", moviqueLightColorScheme().onErrorContainer)
				ColorDisplay("Outline", moviqueLightColorScheme().outline)
				ColorDisplay("Outline Variant", moviqueLightColorScheme().outlineVariant)
				ColorDisplay("Scrim", moviqueLightColorScheme().scrim)
				ColorDisplay("Surface Bright", moviqueLightColorScheme().surfaceBright)
				ColorDisplay("Surface Dim", moviqueLightColorScheme().surfaceDim)
				ColorDisplay("Surface Container", moviqueLightColorScheme().surfaceContainer)
				ColorDisplay("Surface Container High", moviqueLightColorScheme().surfaceContainerHigh)
				ColorDisplay(
					"Surface Container Highest",
					moviqueLightColorScheme().surfaceContainerHighest
				)
				ColorDisplay("Surface Container Low", moviqueLightColorScheme().surfaceContainerLow)
				ColorDisplay(
					"Surface Container Lowest",
					moviqueLightColorScheme().surfaceContainerLowest
				)

				// Dark Color Scheme
				Text(
					text = "Dark Color Scheme",
					style = typography.headlineMedium,
					modifier = Modifier.padding(vertical = 8.dp)
				)
				ColorDisplay("Primary", moviqueDarkColorScheme().primary)
				ColorDisplay("On Primary", moviqueDarkColorScheme().onPrimary)
				ColorDisplay("Primary Container", moviqueDarkColorScheme().primaryContainer)
				ColorDisplay("On Primary Container", moviqueDarkColorScheme().onPrimaryContainer)
				ColorDisplay("Inverse Primary", moviqueDarkColorScheme().inversePrimary)
				ColorDisplay("Secondary", moviqueDarkColorScheme().secondary)
				ColorDisplay("On Secondary", moviqueDarkColorScheme().onSecondary)
				ColorDisplay("Secondary Container", moviqueDarkColorScheme().secondaryContainer)
				ColorDisplay("On Secondary Container", moviqueDarkColorScheme().onSecondaryContainer)
				ColorDisplay("Tertiary", moviqueDarkColorScheme().tertiary)
				ColorDisplay("On Tertiary", moviqueDarkColorScheme().onTertiary)
				ColorDisplay("Tertiary Container", moviqueDarkColorScheme().tertiaryContainer)
				ColorDisplay("On Tertiary Container", moviqueDarkColorScheme().onTertiaryContainer)
				ColorDisplay("Background", moviqueDarkColorScheme().background)
				ColorDisplay("On Background", moviqueDarkColorScheme().onBackground)
				ColorDisplay("Surface", moviqueDarkColorScheme().surface)
				ColorDisplay("On Surface", moviqueDarkColorScheme().onSurface)
				ColorDisplay("Surface Variant", moviqueDarkColorScheme().surfaceVariant)
				ColorDisplay("On Surface Variant", moviqueDarkColorScheme().onSurfaceVariant)
				ColorDisplay("Surface Tint", moviqueDarkColorScheme().surfaceTint)
				ColorDisplay("Inverse Surface", moviqueDarkColorScheme().inverseSurface)
				ColorDisplay("Inverse On Surface", moviqueDarkColorScheme().inverseOnSurface)
				ColorDisplay("Error", moviqueDarkColorScheme().error)
				ColorDisplay("On Error", moviqueDarkColorScheme().onError)
				ColorDisplay("Error Container", moviqueDarkColorScheme().errorContainer)
				ColorDisplay("On Error Container", moviqueDarkColorScheme().onErrorContainer)
				ColorDisplay("Outline", moviqueDarkColorScheme().outline)
				ColorDisplay("Outline Variant", moviqueDarkColorScheme().outlineVariant)
				ColorDisplay("Scrim", moviqueDarkColorScheme().scrim)
				ColorDisplay("Surface Bright", moviqueDarkColorScheme().surfaceBright)
				ColorDisplay("Surface Dim", moviqueDarkColorScheme().surfaceDim)
				ColorDisplay("Surface Container", moviqueDarkColorScheme().surfaceContainer)
				ColorDisplay("Surface Container High", moviqueDarkColorScheme().surfaceContainerHigh)
				ColorDisplay(
					"Surface Container Highest",
					moviqueDarkColorScheme().surfaceContainerHighest
				)
				ColorDisplay("Surface Container Low", moviqueDarkColorScheme().surfaceContainerLow)
				ColorDisplay(
					"Surface Container Lowest",
					moviqueDarkColorScheme().surfaceContainerLowest
				)

				// Typography Showcase
				Text(
					text = "Typography Showcase",
					style = typography.headlineLarge,
					modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
				)

				TypographyDisplay("Display Large", typography.displayLarge)
				TypographyDisplay("Display Medium", typography.displayMedium)
				TypographyDisplay("Display Small", typography.displaySmall)
				TypographyDisplay("Headline Large", typography.headlineLarge)
				TypographyDisplay("Headline Medium", typography.headlineMedium)
				TypographyDisplay("Headline Small", typography.headlineSmall)
				TypographyDisplay("Title Large", typography.titleLarge)
				TypographyDisplay("Title Medium", typography.titleMedium)
				TypographyDisplay("Title Small", typography.titleSmall)
				TypographyDisplay("Body Large", typography.bodyLarge)
				TypographyDisplay("Body Medium", typography.bodyMedium)
				TypographyDisplay("Body Small", typography.bodySmall)
				TypographyDisplay("Label Large", typography.labelLarge)
				TypographyDisplay("Label Medium", typography.labelMedium)
				TypographyDisplay("Label Small", typography.labelSmall)
			}
		}
	}
}

@Composable
fun ColorDisplay(name: String, color: Color) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.size(48.dp)
				.background(color, RoundedCornerShape(8.dp))
				.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
		)
		Spacer(modifier = Modifier.width(16.dp))
		Text(
			text = name,
			style = typography.bodyMedium
		)
	}
}

@Composable
fun TypographyDisplay(name: String, style: TextStyle) {
	Text(
		text = "$name: The quick brown fox",
		style = style,
		modifier = Modifier.padding(vertical = 8.dp)
	)
}