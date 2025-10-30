package org.example.movique.ui.components.mediadetailssection.multipletabssection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel

@Composable
fun MediaDetailsSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	val crewList = when (mediaType) {
		"movie" -> movie?.credits?.crew ?: emptyList()
		"tv" -> tvSeries?.credits?.crew ?: emptyList()
		else -> emptyList()
	}

	val batchSize = 6
	var visibleCount by remember { mutableStateOf(batchSize) }

	if (crewList.isNotEmpty()) {
		Column(modifier = modifier.fillMaxWidth()) {

		}
	} else {
		Box(
			modifier = modifier
				.fillMaxWidth()
				.padding(32.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "No Crew Information Available",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center
			)
		}
	}
}