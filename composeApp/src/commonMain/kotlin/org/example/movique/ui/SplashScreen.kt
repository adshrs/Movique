package org.example.movique.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.example.movique.HomeScreen
import org.example.movique.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(navController: NavHostController) {
	val homeViewModel = koinViewModel<HomeViewModel>()

	LaunchedEffect(Unit) {
		homeViewModel.fetchPopularMovies()
		delay(3000) // Wait for 2 seconds to ensure data fetch starts
		navController.navigate(HomeScreen) {
			popUpTo(navController.graph.startDestinationId) { inclusive = true }
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.background),
		contentAlignment = Alignment.Center
	) {
		Text(
			text = "Movique",
			style = MaterialTheme.typography.displayLarge,
			fontWeight = FontWeight.Bold,
			fontSize = 48.sp,
			color = MaterialTheme.colorScheme.primary
		)
	}
}