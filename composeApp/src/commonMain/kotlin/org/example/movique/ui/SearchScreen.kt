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
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.movique.ui.components.searchbar.CustomSearchBar

@Composable
fun SearchScreen(navController: NavHostController, innerPadding: PaddingValues) {
	var query by remember { mutableStateOf(TextFieldValue("")) }

	Box(
		modifier = Modifier.fillMaxSize(),
	) {
		// Screen Content
		Scaffold {
			LazyColumn(
				modifier = Modifier.fillMaxSize()
			) {
				item { Spacer(modifier = Modifier.height(104.dp)) }
				item { Spacer(modifier = Modifier.height(84.dp)) }
			}
		}

		// Top Bar
		CenterAlignedTopAppBar(
			modifier = Modifier
				.height(88.dp)
				.background(
					Brush.verticalGradient(
						colors = listOf(
							MaterialTheme.colorScheme.background,
							MaterialTheme.colorScheme.background.copy(0.9f)
						)
					)
				),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight()
						.wrapContentWidth(),
					contentAlignment = Alignment.BottomCenter
				) {
					CustomSearchBar(
						modifier = Modifier.padding(horizontal = 4.dp),
						query = query,
						onQueryChange = { query = it },
						placeholder = "Search movies..."
					)
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = Color.Transparent,
			)
		)
	}
}