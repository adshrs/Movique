package org.example.movique

import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsScreen(
	val mediaId: Int,
	val mediaType: String
)

@Serializable
data class MediaListScreen(val category: String)

@Serializable
object SplashScreen

@Serializable
object HomeScreen

@Serializable
object SearchScreen

@Serializable
object FavoritesScreen

@Serializable
object ProfileScreen

@Serializable
object SettingsScreen