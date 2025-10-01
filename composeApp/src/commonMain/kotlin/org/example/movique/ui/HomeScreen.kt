@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
	navController: NavHostController,
	innerPadding: PaddingValues,
	drawerState: DrawerState
) {
	val scope = rememberCoroutineScope()

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		CenterAlignedTopAppBar(
			modifier = Modifier.height(80.dp),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Movique",
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurface,
						textAlign = TextAlign.Center
					)
				}
			},
			navigationIcon = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { scope.launch { drawerState.open() } }) {
						Icon(
							imageVector = Icons.Outlined.Menu,
							contentDescription = "Menu",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = MaterialTheme.colorScheme.surface,
				titleContentColor = MaterialTheme.colorScheme.onSurface,
				navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
				actionIconContentColor = MaterialTheme.colorScheme.onSurface
			)
		)
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {

		}
	}
}