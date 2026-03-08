package org.example.movique.ui.components.mediadetailssection.multipletabssection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.ui.components.tab.NoRippleTab

@Composable
fun MultipleTabsSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	// For Selectable Tabs
	val tabs = listOf(
		"Cast",
		"Crew",
		"Details"
	)
	var selectedTabIndex by remember { mutableStateOf(0) }

	// Selectable Tabs
	PrimaryTabRow(
		modifier = modifier,
		selectedTabIndex = selectedTabIndex,
		containerColor = Color.Transparent,
		divider = {
			HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 0.5.dp)
		}
	) {
		tabs.forEachIndexed { index, title ->
			NoRippleTab(
				title = title,
				index = index,
				selectedTabIndex = selectedTabIndex,
				onClick = { index ->
					selectedTabIndex = index
				}
			)
		}
	}

	// Selected Tab Content
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		when (selectedTabIndex) {
			0 -> {
				MediaCastSection(
					modifier = Modifier,
					mediaType = mediaType,
					movie = movie,
					tvSeries = tvSeries
				)
			}

			1 -> {
				MediaCrewSection(
					modifier = Modifier,
					mediaType = mediaType,
					movie = movie,
					tvSeries = tvSeries
				)
			}

			2 -> {
				MediaDetailsSection(
					modifier = Modifier,
					mediaType = mediaType,
					movie = movie,
					tvSeries = tvSeries
				)
			}
		}
	}
}