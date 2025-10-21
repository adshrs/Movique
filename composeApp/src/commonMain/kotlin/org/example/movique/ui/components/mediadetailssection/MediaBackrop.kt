package org.example.movique.ui.components.mediadetailssection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.util.tools.smoothCinematicVerticalGradientBrush

@Composable
fun MediaBackdrop(
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	Box(
		modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
		contentAlignment = Alignment.Center
	) {
		// Backdrop Image
		AsyncImage(
			model = ImageRequest.Builder(LocalPlatformContext.current)
				.data(
					"https://image.tmdb.org/t/p/original${
						when (mediaType) {
							"movie" -> movie?.backdropPath
							"tv" -> tvSeries?.backdropPath
							else -> null
						}
					}"
				)
				.crossfade(true)
				.precision(Precision.INEXACT)
				.build(),
			contentDescription = when (mediaType) {
				"movie" -> movie?.title
				"tv" -> tvSeries?.name
				else -> ""
			},
			modifier = Modifier.fillMaxSize(),
			contentScale = ContentScale.Crop,
		)
	}
}