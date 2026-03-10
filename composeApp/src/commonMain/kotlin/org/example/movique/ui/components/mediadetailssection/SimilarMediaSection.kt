package org.example.movique.ui.components.mediadetailssection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.NonCancellable.isActive
import org.example.movique.MediaDetailsScreen
import org.example.movique.MediaListScreen
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.ui.components.card.MediaCard
import org.example.movique.ui.components.cardshimmer.ShimmerLoadingRow
import org.example.movique.util.Result.Loading.isLoading

@Composable
fun SimilarMediaSection(
	modifier: Modifier = Modifier,
	navController: NavHostController,
	isLoading: Boolean,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	val similarMediaList = when (mediaType) {
		"movie" -> movie?.similar?.results ?: emptyList()
		"tv" -> tvSeries?.similar?.results ?: emptyList()
		else -> emptyList()
	}

	var activeCardId by rememberSaveable { mutableStateOf<Int?>(null) }

	Column(
		modifier = Modifier.fillMaxWidth().padding(bottom = 28.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Row(
			modifier = Modifier.padding(horizontal = 16.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Box(
				modifier = Modifier
					.size(5.dp, 18.dp)
					.background(
						MaterialTheme.colorScheme.primary,
						shape = MaterialTheme.shapes.large
					)
			)
			Spacer(modifier = Modifier.width(8.dp))
			Text(
				modifier = Modifier.fillMaxWidth().weight(1f),
				text = when (mediaType) {
					"movie" -> "Similar Movies"
					"tv" -> "Similar TV Series"
					else -> "Similar Media"
				},
				style = MaterialTheme.typography.titleMedium,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onSurface
			)
			TextButton(
				modifier = Modifier.height(30.dp),
				onClick = {
//					navController.navigate(MediaListScreen("popular_movies"))
				},
				contentPadding = PaddingValues(
					start = 10.dp,
					end = 4.dp,
					top = 4.dp,
					bottom = 4.dp
				)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(2.dp)
				) {
					Text(
						text = "View more",
						style = MaterialTheme.typography.labelSmall,
					)
					Icon(
						imageVector = Icons.Default.ChevronRight,
						contentDescription = "View More",
						modifier = Modifier.size(16.dp),
						tint = MaterialTheme.colorScheme.onSurface
					)
				}
			}
		}
		if (isLoading) {
			ShimmerLoadingRow()
		} else if (similarMediaList.isEmpty()) {
			Box(
				modifier = modifier
					.fillMaxWidth()
					.padding(16.dp),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = when(mediaType) {
						"movie" -> "No Similar Movies Available"
						"tv" -> "No Similar TV Series Available"
						else -> "No Similar Media Available"
					},
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					textAlign = TextAlign.Center
				)
			}
		} else{
			LazyRow(
				modifier = Modifier.fillMaxSize(),
			) {
				item { Spacer(modifier = Modifier.width(16.dp)) }
				itemsIndexed(similarMediaList) { index, item ->
					val cardId = item.id?.plus(index)?.plus(1) ?: (index + 1)

					MediaCard(
						cardId = cardId,
						fixedWidth = true,
						mediaType = mediaType,
						posterPath = item.posterPath,
						title = when (mediaType) {
							"movie" -> item.title
							"tv" -> item.name
							else -> null
						},
						voteAverage = item.voteAverage,
						releaseDate = when (mediaType) {
							"movie" -> item.releaseDate
							"tv" -> item.firstAirDate
							else -> null
						},
						isActive = activeCardId == cardId,
						onCardClick = { clicked ->
							activeCardId = if (activeCardId == clicked) null else clicked
						},
						onAddActionClick = { },
						onDetailActionClick = {
							navController.navigate(
								MediaDetailsScreen(
									mediaId = item.id ?: 0,
									mediaType = mediaType
								)
							)
						}
					)
					if (index < similarMediaList.size - 1) {
						Spacer(modifier = Modifier.width(8.dp))
					}
				}
				item { Spacer(modifier = Modifier.width(16.dp)) }
			}
		}
	}
}