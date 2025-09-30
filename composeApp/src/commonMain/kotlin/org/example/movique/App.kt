@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.example.movique.theme.MoviqueTheme
import org.example.movique.ui.FavoritesScreen
import org.example.movique.ui.HomeScreen
import org.example.movique.ui.ProfileScreen
import org.example.movique.ui.SearchScreen

@Composable
@Preview
fun App() {
	MoviqueTheme {
		val navController = rememberNavController()
		val currentBackStackEntry by navController.currentBackStackEntryAsState()
		val currentRoute = currentBackStackEntry?.destination?.route

		Scaffold(
			bottomBar = {
				BottomAppBar(
					modifier = Modifier.height(68.dp),
					tonalElevation = 8.dp
				) {
					Row(
						modifier = Modifier.fillMaxSize(),
						horizontalArrangement = Arrangement.SpaceAround,
						verticalAlignment = Alignment.CenterVertically
					) {
						val items = listOf(
							HomeScreen to "Home",
							SearchScreen to "Search",
							FavoritesScreen to "Favorites",
							ProfileScreen to "Profile"
						)

						items.forEach { (screen, label) ->
							val isSelected = currentRoute == screen::class.qualifiedName
							Button(
								onClick = {
									navController.navigate(screen) {
										popUpTo(navController.graph.startDestinationId) {
											saveState = true
										}
										launchSingleTop = true
										restoreState = true
									}
								},
								colors = ButtonDefaults.buttonColors(
									containerColor = Color.Transparent
								),
								contentPadding = PaddingValues(8.dp)
							) {
								Icon(
									imageVector = when (screen) {
										HomeScreen -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
										SearchScreen -> if (isSelected) Icons.Filled.Search else Icons.Outlined.Search
										FavoritesScreen -> if (isSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
										ProfileScreen -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
										else -> Icons.Default.Info // Fallback (not used)
									},
									contentDescription = label,
									modifier = Modifier.size(28.dp),
									tint = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
								)
							}
						}
					}
				}
			}
		) { innerPadding ->
			NavHost(
				navController = navController,
				startDestination = HomeScreen,
			) {
				composable<HomeScreen> { HomeScreen(navController, innerPadding) }
				composable<SearchScreen> { SearchScreen(navController, innerPadding) }
				composable<FavoritesScreen> { FavoritesScreen(navController, innerPadding) }
				composable<ProfileScreen> { ProfileScreen(navController, innerPadding) }
			}
		}
	}
}