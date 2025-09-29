package org.example.movique.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import movique.composeapp.generated.resources.PlayfairDisplay_Bold
import movique.composeapp.generated.resources.PlayfairDisplay_Regular
import movique.composeapp.generated.resources.Poppins_Bold
import movique.composeapp.generated.resources.Poppins_Medium
import movique.composeapp.generated.resources.Poppins_Regular
import movique.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font


// Custom typography scheme for Movique
@Composable
fun moviqueTypography(): Typography {
	return Typography(
		// Display styles (large, prominent text, e.g., movie titles)
		displayLarge = TextStyle(
			fontFamily = PlayfairDisplay,
			fontWeight = FontWeight.Bold,
			fontSize = 57.sp,
			lineHeight = 64.sp,
			letterSpacing = (-0.25).sp
		),
		displayMedium = TextStyle(
			fontFamily = PlayfairDisplay,
			fontWeight = FontWeight.Bold,
			fontSize = 45.sp,
			lineHeight = 52.sp,
			letterSpacing = 0.sp
		),
		displaySmall = TextStyle(
			fontFamily = PlayfairDisplay,
			fontWeight = FontWeight.Normal,
			fontSize = 36.sp,
			lineHeight = 44.sp,
			letterSpacing = 0.sp
		),

		// Headline styles (e.g., section headers, movie categories)
		headlineLarge = TextStyle(
			fontFamily = PlayfairDisplay,
			fontWeight = FontWeight.Bold,
			fontSize = 32.sp,
			lineHeight = 40.sp,
			letterSpacing = 0.sp
		),
		headlineMedium = TextStyle(
			fontFamily = PlayfairDisplay,
			fontWeight = FontWeight.Normal,
			fontSize = 28.sp,
			lineHeight = 36.sp,
			letterSpacing = 0.sp
		),
		headlineSmall = TextStyle(
			fontFamily = PlayfairDisplay,
			fontWeight = FontWeight.Normal,
			fontSize = 24.sp,
			lineHeight = 32.sp,
			letterSpacing = 0.sp
		),

		// Title styles (e.g., movie names, card titles)
		titleLarge = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Bold,
			fontSize = 22.sp,
			lineHeight = 28.sp,
			letterSpacing = 0.sp
		),
		titleMedium = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Medium,
			fontSize = 16.sp,
			lineHeight = 24.sp,
			letterSpacing = 0.15.sp
		),
		titleSmall = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Medium,
			fontSize = 14.sp,
			lineHeight = 20.sp,
			letterSpacing = 0.1.sp
		),

		// Body styles (e.g., descriptions, reviews)
		bodyLarge = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Normal,
			fontSize = 16.sp,
			lineHeight = 24.sp,
			letterSpacing = 0.5.sp
		),
		bodyMedium = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Normal,
			fontSize = 14.sp,
			lineHeight = 20.sp,
			letterSpacing = 0.25.sp
		),
		bodySmall = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Normal,
			fontSize = 12.sp,
			lineHeight = 16.sp,
			letterSpacing = 0.4.sp
		),

		// Label styles (e.g., buttons, tags, ratings)
		labelLarge = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Medium,
			fontSize = 14.sp,
			lineHeight = 20.sp,
			letterSpacing = 0.1.sp
		),
		labelMedium = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Medium,
			fontSize = 12.sp,
			lineHeight = 16.sp,
			letterSpacing = 0.5.sp
		),
		labelSmall = TextStyle(
			fontFamily = Poppins,
			fontWeight = FontWeight.Medium,
			fontSize = 11.sp,
			lineHeight = 16.sp,
			letterSpacing = 0.5.sp
		)
	)
}

val Poppins @Composable get() = FontFamily(
	Font(Res.font.Poppins_Regular, FontWeight.Normal),
	Font(Res.font.Poppins_Medium, FontWeight.Medium),
	Font(Res.font.Poppins_Bold, FontWeight.Bold)
)

val PlayfairDisplay @Composable get() = FontFamily(
	Font(Res.font.PlayfairDisplay_Regular, FontWeight.Normal),
	Font(Res.font.PlayfairDisplay_Bold, FontWeight.Bold)
)