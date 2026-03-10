package org.example.movique.ui.components.mediadetailssection

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import io.ktor.websocket.Frame.Companion.byType
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.theme.extraColors
import org.example.movique.theme.titleRegular
import org.example.movique.ui.components.badge.MediaStatusBadge
import org.example.movique.ui.components.badge.MediaTypeBadge
import org.example.movique.ui.components.chip.GenreChip
import org.example.movique.ui.components.divider.DottedDivider
import org.example.movique.util.tools.Constants.NA
import org.example.movique.util.tools.toHourMinuteFormat
import org.example.movique.util.tools.toSeasonText
import kotlin.math.round

@Composable
fun MediaMainInfoSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?,
	retrieveTitleTopPx: (Float) -> Unit
) {
	Column(
		modifier = modifier
	) {
		Card(
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			shape = MaterialTheme.shapes.large,
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.4f),
				contentColor = MaterialTheme.colorScheme.onSurface
			)
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 4.dp, vertical = 4.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				val releaseDate = when (mediaType) {
					"movie" -> movie?.releaseDate
					"tv" -> tvSeries?.firstAirDate
					else -> null
				}
				val runtime = when (mediaType) {
					"movie" -> movie?.runtime
					"tv" -> tvSeries?.numberOfSeasons
					else -> null
				}
				val rating = when (mediaType) {
					"movie" -> movie?.voteAverage
					"tv" -> tvSeries?.voteAverage
					else -> null
				}
				MediaTypeBadge(
					mediaType = mediaType,
					textStyle = MaterialTheme.typography.labelLarge
				)
				runtime?.let {
					Row(
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(
							text = "  •  ",
							style = MaterialTheme.typography.labelLarge
						)
						Icon(
							imageVector = Icons.Default.Schedule,
							contentDescription = "Runtime",
							modifier = Modifier.size(14.dp)
						)
						Spacer(modifier = Modifier.width(4.dp))
						Text(
							modifier = Modifier,
							text = when (mediaType) {
								"movie" -> if (runtime == 0) NA else runtime.toHourMinuteFormat()
								"tv" -> runtime.toSeasonText()
								else -> NA
							},
							style = MaterialTheme.typography.labelLarge
						)
					}
					Spacer(modifier = Modifier.width(8.dp))
				}
				Spacer(Modifier.weight(1f))
				Badge(
					containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(
						0.9f
					),
					contentColor = MaterialTheme.colorScheme.onSurface
				) {
					Row(
						modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(
							imageVector = Icons.Default.Star,
							contentDescription = "Rating",
							modifier = Modifier.size(16.dp),
							tint = MaterialTheme.extraColors.ratingGold
						)
						Spacer(modifier = Modifier.width(4.dp))
						Text(
							modifier = Modifier,
							text = if (rating != null) "${round(rating * 10) / 10}" else NA,
							style = MaterialTheme.typography.labelLarge
						)
					}
				}
			}
		}

		Spacer(modifier = Modifier.height(12.dp))

		Row(
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			verticalAlignment = Alignment.Top
		) {
			// Poster Image
			Column {
				OutlinedCard(
					modifier = Modifier
						.width(112.dp)
						.clickable(
							indication = null,
							interactionSource = null,
							onClick = { }
						),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.surfaceContainer,
						contentColor = MaterialTheme.colorScheme.onSurface
					),
					border = BorderStroke(
						1.dp,
						MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
					)
				) {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.aspectRatio(2f / 3f)
							.background(MaterialTheme.colorScheme.surfaceVariant),
						contentAlignment = Alignment.Center
					) {
						AsyncImage(
							model = ImageRequest.Builder(LocalPlatformContext.current)
								.data(
									"https://image.tmdb.org/t/p/w500${
										when (mediaType) {
											"movie" -> movie?.posterPath
											"tv" -> tvSeries?.posterPath
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
						MediaStatusBadge(
							mediaType = mediaType,
							status = when (mediaType) {
								"movie" -> movie?.status ?: NA
								"tv" -> tvSeries?.status ?: NA
								else -> NA
							},
							modifier = Modifier
								.align(Alignment.TopCenter)
								.padding(6.dp)
						)
					}
				}

				Spacer(modifier = Modifier.height(12.dp))

				Button(
					onClick = { },
					modifier = Modifier.width(112.dp).height(28.dp),
					contentPadding = PaddingValues(0.dp),
					colors = ButtonDefaults.buttonColors(
						containerColor = MaterialTheme.colorScheme.primaryContainer,
						contentColor = MaterialTheme.colorScheme.onPrimaryContainer
					)
				) {
					Icon(
						imageVector = Icons.Outlined.PlayCircle,
						contentDescription = "Trailer",
						modifier = Modifier.size(14.dp)
					)
					Spacer(Modifier.width(6.dp))
					Text(
						text = "Trailer",
						style = MaterialTheme.typography.labelLarge,
					)
				}
			}

			Spacer(Modifier.width(20.dp))

			// Main Info
			Column(
				modifier = Modifier.weight(1f),
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				val title = when (mediaType) {
					"movie" -> movie?.title
					"tv" -> tvSeries?.name
					else -> null
				}
				val originalTitle = when (mediaType) {
					"movie" -> movie?.originalTitle
					"tv" -> tvSeries?.originalName
					else -> null
				}
				val releaseDate = when (mediaType) {
					"movie" -> movie?.releaseDate
					"tv" -> tvSeries?.firstAirDate
					else -> null
				}
				val by = when (mediaType) {
					"movie" -> movie?.credits?.crew
						?.filter { it?.job == "Director" }
						?.mapNotNull { it?.name } ?: emptyList()

					"tv" -> if (!tvSeries?.createdBy.isNullOrEmpty()) {
						tvSeries.createdBy.mapNotNull { it?.name }
					} else {
						tvSeries?.credits?.crew
							?.filter { it?.job == "Director" || it?.job == "Series Director" }
							?.mapNotNull { it?.name } ?: emptyList()
					}

					else -> emptyList()
				}
				val byType = when (mediaType) {
					"movie" -> "DIRECTED BY"
					"tv" -> if (!tvSeries?.createdBy.isNullOrEmpty()) {
						"CREATED BY"
					} else {
						if (tvSeries?.credits?.crew
								?.filter { it?.job == "Director" || it?.job == "Series Director" }
								?.mapNotNull { it?.name }
								?.isNotEmpty() == true
						)
						{
							"DIRECTED BY"
						} else {
							""
						}
					}

					else -> ""
				}
				val genres = when (mediaType) {
					"movie" -> movie?.genres?.mapNotNull { it?.name } ?: emptyList()
					"tv" -> tvSeries?.genres?.mapNotNull { it?.name } ?: emptyList()
					else -> emptyList()
				}

				Text(
					text = title ?: "",
					style = MaterialTheme.typography.titleRegular,
					color = MaterialTheme.colorScheme.onSurface,
					modifier = Modifier.onGloballyPositioned { coordinatess ->
						// boundsInWindow gives a Rect in window coordinates
						retrieveTitleTopPx(coordinatess.boundsInWindow().top)
					}
				)
				if (!originalTitle.isNullOrEmpty() && originalTitle != title) {
					Text(
						text = originalTitle,
						style = MaterialTheme.typography.labelMedium,
						fontStyle = FontStyle.Italic,
						color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
					)
				}
				if (!releaseDate.isNullOrEmpty()) {
					Text(
						text = releaseDate.take(4) + if (!by.isEmpty()) "  •  $byType" else "",
						style = MaterialTheme.typography.labelLarge,
						color = MaterialTheme.colorScheme.tertiary.copy(0.8f),
					)
				}
				if (by.isNotEmpty()) {
					Text(
						text = by.joinToString(", "),
						style = MaterialTheme.typography.labelLarge,
						color = MaterialTheme.colorScheme.tertiary.copy(0.8f),
					)
				}

				FlowRow(
					modifier = Modifier.padding(vertical = 8.dp),
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					genres.forEach { genre ->
						GenreChip(genre)
					}
				}
			}
		}

		Spacer(modifier = Modifier.height(20.dp))

		// Tagline & Overview
		Column(
			modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
		) {
			val tagline = when (mediaType) {
				"movie" -> movie?.tagline
				"tv" -> tvSeries?.tagline
				else -> null
			}
			val overview = when (mediaType) {
				"movie" -> movie?.overview ?: "No overview available."
				"tv" -> tvSeries?.overview ?: "No overview available."
				else -> "No overview available."
			}

			var isOverviewOverflowing by remember { mutableStateOf(false) }
			var wasOverviewOverflowing by remember { mutableStateOf(false) }
			var isOverviewExpanded by rememberSaveable { mutableStateOf(false) }

			if (!tagline.isNullOrBlank()) {
				Text(
					text = tagline,
					style = MaterialTheme.typography.labelLarge,
					fontStyle = FontStyle.Italic,
					color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
					modifier = Modifier.padding(horizontal = 16.dp)
				)
				Spacer(modifier = Modifier.height(12.dp))
			}
			Text(
				text = overview.ifBlank { "No overview available." },
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
				maxLines = if (isOverviewExpanded) Int.MAX_VALUE else 3,
				overflow = TextOverflow.Ellipsis,
				modifier = Modifier
					.padding(horizontal = 16.dp)
					.clickable(
						indication = null,
						interactionSource = remember { MutableInteractionSource() },
						onClick = { isOverviewExpanded = !isOverviewExpanded }
					)
					.animateContentSize(),
				onTextLayout = { textLayoutResult ->
					isOverviewOverflowing = textLayoutResult.hasVisualOverflow
					if (textLayoutResult.hasVisualOverflow) {
						wasOverviewOverflowing = true
					}
				}
			)

			if (wasOverviewOverflowing) {
				Row(
					modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					val rotation by animateFloatAsState(
						targetValue = if (isOverviewExpanded) 180f else 0f,
						animationSpec = tween(
							durationMillis = 300,
							easing = LinearOutSlowInEasing
						),
						label = "arrowRotation"
					)

					DottedDivider(
						modifier = Modifier
							.weight(1f)
							.clickable(
								indication = null,
								interactionSource = remember { MutableInteractionSource() },
								onClick = { isOverviewExpanded = !isOverviewExpanded }
							),
						thickness = 1.dp,
						dashLength = 4f,     // smaller value → more dot-like
						gapLength  = 20f      // adjust gap for desired look
					)

					OutlinedButton(
						modifier = Modifier.height(24.dp),
						onClick = { isOverviewExpanded = !isOverviewExpanded },
						contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp, start = 12.dp, end = 6.dp),
						colors = ButtonDefaults.outlinedButtonColors(
							contentColor = MaterialTheme.colorScheme.primary
						),
						border = BorderStroke(0.5.dp,DividerDefaults.color)
					) {
						Text(
							text = if (!isOverviewExpanded) "See more" else "See less",
							style = MaterialTheme.typography.labelMedium,
							modifier = Modifier
								.clickable(
									indication = null,
									interactionSource = remember { MutableInteractionSource() },
									onClick = { isOverviewExpanded = !isOverviewExpanded }
								)
						)
						Icon(
							imageVector = Icons.Default.KeyboardArrowDown,
							contentDescription = if (isOverviewExpanded) "See less" else "See more",
							modifier = Modifier
								.size(20.dp)
								.graphicsLayer { rotationZ = rotation }
								.clickable(
									indication = null,
									interactionSource = remember { MutableInteractionSource() },
									onClick = { isOverviewExpanded = !isOverviewExpanded }
								)
						)
					}


					DottedDivider(
						modifier = Modifier
							.weight(1f)
							.clickable(
								indication = null,
								interactionSource = remember { MutableInteractionSource() },
								onClick = { isOverviewExpanded = !isOverviewExpanded }
							),
						thickness = 1.dp,
						dashLength = 4f,
						gapLength  = 20f
					)
				}
			}
		}
	}
}