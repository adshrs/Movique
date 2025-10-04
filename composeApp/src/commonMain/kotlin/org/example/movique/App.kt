@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.movique.di.sharedModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.example.movique.theme.MoviqueTheme
import org.example.movique.theme.thememode.ThemeMode
import org.example.movique.theme.thememode.ThemeRepository
import org.example.movique.ui.FavoritesScreen
import org.example.movique.ui.HomeScreen
import org.example.movique.ui.ProfileScreen
import org.example.movique.ui.SearchScreen
import org.example.movique.ui.SettingsScreen
import org.example.movique.ui.SplashScreen
import org.koin.compose.getKoin
import org.koin.core.context.startKoin

@Composable
@Preview
fun MoviqueApp() {
	val repo: ThemeRepository = getKoin().get()// from Koin-Compose or getKoin().get()
	val themeFlow = remember { repo.observeTheme() }
	val theme by themeFlow.collectAsState(initial = ThemeMode.SYSTEM)

	MoviqueTheme(theme) {
		val navController = rememberNavController()
		val currentBackStackEntry by navController.currentBackStackEntryAsState()
		val currentRoute = currentBackStackEntry?.destination?.route
		val isHomeScreen = currentRoute == HomeScreen::class.qualifiedName
		val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
		val scope = rememberCoroutineScope()

		MoviqueAppContent(drawerState, isHomeScreen, scope, navController, currentRoute)
	}
}

@Composable
private fun MoviqueAppContent(
	drawerState: DrawerState,
	isHomeScreen: Boolean,
	scope: CoroutineScope,
	navController: NavHostController,
	currentRoute: String?
) {
	ModalNavigationDrawer(
		drawerState = drawerState,
		gesturesEnabled = isHomeScreen, // Enable drawer gestures only on Home screen
		drawerContent = {
			MoviqueModalDrawerSheet(scope, drawerState, navController, currentRoute)
		}
	) {
//		Scaffold(
//			modifier = Modifier.navigationBarsPadding(),
//			bottomBar = {
//				BottomAppBar(
//					modifier = Modifier
//						.height(52.dp),
//					tonalElevation = 8.dp
//				) {
//					Row(
//						modifier = Modifier.fillMaxSize(),
//						horizontalArrangement = Arrangement.SpaceAround,
//						verticalAlignment = Alignment.CenterVertically
//					) {
//						val items = listOf(
//							HomeScreen to "Home",
//							SearchScreen to "Search",
//							FavoritesScreen to "Favorites",
//							ProfileScreen to "Profile"
//						)
//
//						items.forEach { (screen, label) ->
//							val isSelected = currentRoute == screen::class.qualifiedName
//							Button(
//								onClick = {
//									navController.navigate(screen) {
//										popUpTo(navController.graph.startDestinationId) {
//											saveState = true
//										}
//										launchSingleTop = true
//										restoreState = true
//									}
//								},
//								colors = ButtonDefaults.buttonColors(
//									containerColor = Color.Transparent
//								),
//								contentPadding = PaddingValues(0.dp)
//							) {
//								Icon(
//									imageVector = when (screen) {
//										HomeScreen -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
//										SearchScreen -> if (isSelected) Icons.Filled.Search else Icons.Outlined.Search
//										FavoritesScreen -> if (isSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
//										ProfileScreen -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
//										else -> Icons.Default.Info // Fallback (not used)
//									},
//									contentDescription = label,
//									modifier = Modifier.size(28.dp),
//									tint = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
//								)
//							}
//						}
//					}
//				}
//			}
//		) { innerPadding ->
		Box(
			modifier = Modifier.fillMaxSize()
		) {
			NavHost(
				navController = navController,
				startDestination = HomeScreen,
				enterTransition = { EnterTransition.None },
				exitTransition = { ExitTransition.None },
				popEnterTransition = { EnterTransition.None },
				popExitTransition = { ExitTransition.None }
			) {
				composable<SplashScreen> { SplashScreen(navController) }
				composable<HomeScreen> { HomeScreen(navController, PaddingValues(), drawerState) }
				composable<SearchScreen> { SearchScreen(navController, PaddingValues()) }
				composable<FavoritesScreen> { FavoritesScreen(navController, PaddingValues()) }
				composable<ProfileScreen> { ProfileScreen(navController, PaddingValues()) }
				composable<SettingsScreen> { SettingsScreen(navController, PaddingValues()) }
			}
			Box(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.background(
						Brush.verticalGradient(
							colors = listOf(
								BottomAppBarDefaults.containerColor.copy(0.7f),
								BottomAppBarDefaults.containerColor
							)
						)
					)
			) {
				Box(
					modifier = Modifier.navigationBarsPadding()
				) {
					BottomAppBar(
						modifier = Modifier
							.height(52.dp),
						tonalElevation = 8.dp,
						containerColor = Color.Transparent
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
									contentPadding = PaddingValues(0.dp)
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
			}
		}
//		}
	}
}

// Drawer Sheet Content
@Composable
private fun MoviqueModalDrawerSheet(
	scope: CoroutineScope,
	drawerState: DrawerState,
	navController: NavHostController,
	currentRoute: String?
) {
	ModalDrawerSheet(
		modifier = Modifier.width(IntrinsicSize.Max)
	) {
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			// Drawer Header
			Row(
				modifier = Modifier
					.padding(top = 4.dp, bottom = 8.dp)
					.padding(horizontal = 12.dp),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(2.dp)
			) {
				IconButton(onClick = { scope.launch { drawerState.close() } }) {
					Icon(
						imageVector = Icons.Outlined.Menu,
						contentDescription = "Menu",
						tint = MaterialTheme.colorScheme.onSurface
					)
				}
				Text(
					text = "Movique Menu",
					style = MaterialTheme.typography.titleLarge,
					color = MaterialTheme.colorScheme.onSurface,
				)
			}

			//Explore Section
			Text(
				text = "Explore",
				style = MaterialTheme.typography.titleSmall,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(start = 24.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.Movie,
						contentDescription = "Browse Movies"
					)
				},
				label = { Text("Browse Movies", style = MaterialTheme.typography.titleMedium) },
				selected = false,
				onClick = {
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.padding(top = 4.dp, bottom = 2.dp)
					.height(48.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.Visibility,
						contentDescription = "Watched"
					)
				},
				label = { Text("Watched", style = MaterialTheme.typography.titleMedium) },
				selected = false,
				onClick = {
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp, vertical = 2.dp)
					.height(48.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.WatchLater,
						contentDescription = "Watchlist"
					)
				},
				label = { Text("Watchlist", style = MaterialTheme.typography.titleMedium) },
				selected = false,
				onClick = {
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp, vertical = 2.dp)
					.height(48.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.FavoriteBorder,
						contentDescription = "Favorites"
					)
				},
				label = { Text("Favorites", style = MaterialTheme.typography.titleMedium) },
				selected = currentRoute == FavoritesScreen::class.qualifiedName,
				onClick = {
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.padding(top = 2.dp, bottom = 4.dp)
					.height(48.dp)
			)

			// Tools Section
			Text(
				text = "Tools",
				style = MaterialTheme.typography.titleSmall,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(start = 24.dp, top = 8.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.Shuffle,
						contentDescription = "Random Movie Generator"
					)
				},
				label = {
					Text(
						"Random Movie Generator",
						style = MaterialTheme.typography.titleMedium
					)
				},
				selected = false,
				onClick = {
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp, vertical = 4.dp)
					.height(48.dp)
			)

			// Account Section
			Text(
				text = "Account",
				style = MaterialTheme.typography.titleSmall,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(start = 24.dp, top = 8.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.Person,
						contentDescription = "Profile"
					)
				},
				label = { Text("Profile", style = MaterialTheme.typography.titleMedium) },
				selected = currentRoute == ProfileScreen::class.qualifiedName,
				onClick = {
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.padding(top = 4.dp, bottom = 2.dp)
					.height(48.dp)
			)
			NavigationDrawerItem(
				icon = {
					Icon(
						modifier = Modifier.padding(end = 8.dp).size(20.dp),
						imageVector = Icons.Outlined.Settings,
						contentDescription = "Settings"
					)
				},
				label = { Text("Settings", style = MaterialTheme.typography.titleMedium) },
				selected = false,
				onClick = {
					navController.navigate(SettingsScreen) {
						popUpTo(navController.graph.startDestinationId) {
							saveState = true
						}
						launchSingleTop = true
						restoreState = true
					}
					scope.launch { drawerState.close() }
				},
				modifier = Modifier
					.padding(horizontal = 8.dp)
					.padding(top = 2.dp, bottom = 4.dp)
					.height(48.dp)
			)
		}
	}
}