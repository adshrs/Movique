@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.movique.ui.components.settingsoption.ThemeSelector

@Composable
fun SettingsScreen(
	navController: NavHostController,
	innerPadding: PaddingValues
) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		TopAppBar(
			modifier = Modifier.height(80.dp),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Settings",
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
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowBack,
							contentDescription = "Back",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = MaterialTheme.colorScheme.surface,
				titleContentColor = MaterialTheme.colorScheme.onSurface,
				actionIconContentColor = MaterialTheme.colorScheme.onSurface
			)
		)

		// Screen Content
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(innerPadding)
				.padding(horizontal = 16.dp)
				.verticalScroll(rememberScrollState())
		) {
			// General Section
			Column(
				modifier = Modifier
					.fillMaxWidth(),
			) {
				Text(
					text = "General",
					style = MaterialTheme.typography.titleSmall,
					color = MaterialTheme.colorScheme.primary
				)
				Spacer(modifier = Modifier.height(12.dp))
				ThemeSelector()
			}
		}
	}
}