package org.example.movique.ui.components.mediadetailssection.multipletabssection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.theme.titleRegular
import org.example.movique.ui.components.chip.GenreChip
import org.example.movique.util.tools.Constants.NA
import org.example.movique.util.tools.toEpisodeText
import org.example.movique.util.tools.toHourMinuteFormat
import org.example.movique.util.tools.toSeasonText

@Composable
fun MediaDetailsSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	val genreList = when (mediaType) {
		"movie" -> movie?.genres ?: emptyList()
		"tv" -> tvSeries?.genres ?: emptyList()
		else -> emptyList()
	}

	val productionCompanyList = when (mediaType) {
		"movie" -> movie?.productionCompanies ?: emptyList()
		"tv" -> tvSeries?.productionCompanies ?: emptyList()
		else -> emptyList()
	}

	val originCountryList = when (mediaType) {
		"movie" -> movie?.originCountry ?: emptyList()
		"tv" -> tvSeries?.originCountry ?: emptyList()
		else -> emptyList()
	}

	val originalLanguage = when (mediaType) {
		"movie" -> movie?.originalLanguage ?: NA
		"tv" -> tvSeries?.originalLanguage ?: NA
		else -> NA
	}

	val spokenLanguageList = when (mediaType) {
		"movie" -> movie?.spokenLanguages ?: emptyList()
		"tv" -> tvSeries?.spokenLanguages ?: emptyList()
		else -> emptyList()
	}

	val releaseOrFirstAirDate = when (mediaType) {
		"movie" -> movie?.releaseDate ?: NA
		"tv" -> tvSeries?.firstAirDate ?: NA
		else -> NA
	}

	val runtimeOrSeasonEpisode = when (mediaType) {
		"movie" -> if (movie?.runtime == 0) NA else movie?.runtime?.toHourMinuteFormat() ?: NA
		"tv" -> tvSeries?.numberOfSeasons?.toSeasonText() + "  •  " + tvSeries?.numberOfEpisodes?.toEpisodeText()
		else -> NA
	}

	val batchSize = 6
	var visibleCount by remember { mutableStateOf(batchSize) }

	if (genreList.isNotEmpty()) {
		Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
			// Genres Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Genres",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
					Box(
						modifier = Modifier
							.size(30.dp, 24.dp)
							.clip(CircleShape)
							.clickable(
								onClick = { }
							)
					) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
							contentDescription = "Go to Details",
							modifier = Modifier
								.align(Alignment.Center)
								.size(14.dp),
							tint = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
				}
				Spacer(modifier = Modifier.height(12.dp))
				FlowRow(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					genreList.forEach { genre ->
						if (!genre?.name.isNullOrBlank()) {
							GenreChip(
								text = genre.name,
								textStyle = MaterialTheme.typography.labelMedium,
								textPadding = PaddingValues(horizontal = 12.dp, vertical = 5.dp)
							)
						}
					}
				}
			}

			Spacer(modifier = Modifier.height(22.dp))

			// Production Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Production",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
					Box(
						modifier = Modifier
							.size(30.dp, 24.dp)
							.clip(CircleShape)
							.clickable(
								onClick = { }
							)
					) {
						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
							contentDescription = "Go to Details",
							modifier = Modifier
								.align(Alignment.Center)
								.size(14.dp),
							tint = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
				}
				Spacer(modifier = Modifier.height(12.dp))
				FlowRow(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					productionCompanyList.forEach { company ->
						OutlinedCard(
							modifier = Modifier
								.size(46.dp),
							colors = CardDefaults.cardColors(
								containerColor = Color.White
							),
							border = BorderStroke(
								1.dp,
								MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
							)
						) {
							if (!company?.logoPath.isNullOrBlank()) {
								AsyncImage(
									model = ImageRequest.Builder(LocalPlatformContext.current)
										.data("https://image.tmdb.org/t/p/w200${company.logoPath}")
										.crossfade(true)
										.precision(Precision.INEXACT)
										.build(),
									contentDescription = company.name ?: "Company Logo",
									modifier = Modifier.fillMaxSize().padding(4.dp),
									contentScale = ContentScale.Fit
								)
							} else {
								Box(
									modifier = Modifier.fillMaxSize()
								) {
									Text(
										text = "Unknown",
										modifier = Modifier.align(Alignment.Center).padding(4.dp),
										textAlign = TextAlign.Center,
										style = MaterialTheme.typography.labelSmall,
										fontSize = 6.sp,
										color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
									)
								}
							}
						}
					}
				}
			}

			Spacer(modifier = Modifier.height(22.dp))

			// Release Date/First Air Date Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = when(mediaType) {
							"movie" -> "Release Date"
							"tv" -> "First Air Date"
							else -> NA
						},
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				Spacer(modifier = Modifier.height(12.dp))
				Text(
					text = releaseOrFirstAirDate,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
				)
			}

			Spacer(modifier = Modifier.height(22.dp))

			// Runtime/Seasons & Episodes Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = when(mediaType) {
							"movie" -> "Runtime"
							"tv" -> "Seasons & Episodes"
							else -> NA
						},
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				Spacer(modifier = Modifier.height(12.dp))
				Text(
					text = runtimeOrSeasonEpisode,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
				)
			}

			Spacer(modifier = Modifier.height(22.dp))

			// Country of Origin Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Country of Origin",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				Spacer(modifier = Modifier.height(12.dp))
				FlowRow(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					originCountryList.forEachIndexed { index, country ->
						val isLast = index == originCountryList.filterNot { it.isNullOrBlank() }.lastIndex
						if (!country.isNullOrBlank()) {
							Text(
								text = if (isLast) country else "$country,",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Medium,
								color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
							)
						}
					}
				}
			}

			Spacer(modifier = Modifier.height(22.dp))

			// Original Language Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Original Language",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				Spacer(modifier = Modifier.height(12.dp))

				Text(
					text = originalLanguage,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
				)
			}

			Spacer(modifier = Modifier.height(22.dp))

			// Spoken Languages Section
			Column {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "Spoken Languages",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				Spacer(modifier = Modifier.height(12.dp))
				FlowRow(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					spokenLanguageList.forEachIndexed { index, language ->
						val isLast = index == spokenLanguageList.filterNot { it?.iso6391.isNullOrBlank() }.lastIndex
						if (!language?.iso6391.isNullOrBlank()) {
							Text(
								text = if (isLast) language.iso6391 else "${language.iso6391},",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Medium,
								color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
							)
						}
					}
				}
			}
		}
	} else {
		Box(
			modifier = modifier
				.fillMaxWidth()
				.padding(32.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "No Details Available",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center
			)
		}
	}
	HorizontalDivider(
		modifier = Modifier.fillMaxWidth(),
		thickness = 0.5.dp
	)
}