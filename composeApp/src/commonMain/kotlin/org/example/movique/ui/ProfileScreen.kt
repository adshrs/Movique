@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController, innerPadding: PaddingValues) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		// Screen Content
		Scaffold {
			LazyColumn(
				modifier = Modifier.fillMaxSize(),
			) {
				item { Spacer(modifier = Modifier.height(96.dp)) }
				item { Spacer(modifier = Modifier.height(84.dp)) }

			}
		}

		// Top Bar
		TopAppBar(
			modifier = Modifier
				.height(80.dp)
				.background(
					Brush.verticalGradient(
						colors = listOf(
							BottomAppBarDefaults.containerColor,
							BottomAppBarDefaults.containerColor.copy(0.9f)
						)
					)
				),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Profile",
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurface
					)
				}
			},
			navigationIcon = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { }) {
						Icon(
							imageVector = Icons.Default.Person,
							contentDescription = "Profile",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			actions = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { /* TODO: Open profile settings */ }) {
						Icon(
							imageVector = Icons.Default.MoreVert,
							contentDescription = "Settings",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = Color.Transparent,
				titleContentColor = MaterialTheme.colorScheme.onSurface,
				actionIconContentColor = MaterialTheme.colorScheme.onSurface
			)
		)
	}
}