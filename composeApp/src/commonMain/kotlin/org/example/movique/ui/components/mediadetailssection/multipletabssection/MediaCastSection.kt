package org.example.movique.ui.components.mediadetailssection.multipletabssection

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel

@Composable
fun MediaCastSection(
	modifier: Modifier = Modifier,
	mediaType: String,
	movie: MovieDetailsResponseModel?,
	tvSeries: TvSeriesDetailsResponseModel?
) {
	val castList = when (mediaType) {
		"movie" -> movie?.credits?.cast ?: emptyList()
		"tv" -> tvSeries?.credits?.cast ?: emptyList()
		else -> emptyList()
	}

	val batchSize = 6
	var visibleCount by rememberSaveable { mutableStateOf(batchSize) }

	if (castList.isNotEmpty()) {
		Column(modifier = modifier.fillMaxWidth().animateContentSize()) {
			// Show limited items
			castList.take(visibleCount).forEachIndexed { index, cast ->
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.clickable { /* TODO: navigate to cast details */ }
				) {
					if (index == 0) Spacer(modifier = Modifier.height(16.dp))
					else Spacer(modifier = Modifier.height(8.dp))

					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp),
						verticalAlignment = Alignment.Top,
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						OutlinedCard(
							modifier = Modifier.size(42.dp),
							colors = CardDefaults.cardColors(
								containerColor = MaterialTheme.colorScheme.surfaceContainer,
								contentColor = MaterialTheme.colorScheme.onSurface
							),
							shape = CircleShape,
							border = BorderStroke(
								1.dp,
								MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
							)
						) {
							AsyncImage(
								model = ImageRequest.Builder(LocalPlatformContext.current)
									.data("https://image.tmdb.org/t/p/w200${cast?.profilePath}")
									.crossfade(true)
									.precision(Precision.INEXACT)
									.build(),
								contentDescription = cast?.name ?: "Cast Image",
								modifier = Modifier.fillMaxSize(),
								contentScale = ContentScale.Crop
							)
						}

						Column(
							modifier = Modifier.weight(1f),
							verticalArrangement = Arrangement.spacedBy(4.dp)
						) {
							Text(
								text = cast?.name?.ifBlank { "Unknown Person" } ?: "Unknown Person",
								style = MaterialTheme.typography.bodyMedium,
								fontWeight = FontWeight.Medium,
								color = MaterialTheme.colorScheme.onSurface.copy(0.9f),
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
							Text(
								text = cast?.character?.ifBlank { "Unknown Character" }
									?: "Unknown Character",
								style = MaterialTheme.typography.bodySmall,
								color = MaterialTheme.colorScheme.onSurfaceVariant,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}

						Icon(
							imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
							contentDescription = "Go to Cast Details",
							modifier = Modifier
								.size(16.dp)
								.align(Alignment.CenterVertically),
							tint = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}

					Spacer(modifier = Modifier.height(8.dp))
				}
			}
		}
	} else {
		Box(
			modifier = modifier
				.fillMaxWidth()
				.padding(16.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "No Cast Information Available",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center
			)
		}
	}
	// Divider and bottom controls
	Row(
		modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		HorizontalDivider(
			modifier = Modifier
				.weight(1f),
			thickness = 0.5.dp
		)
		// Show controls at bottom
		if (castList.size > batchSize) {
			// Collapse Button
			if (visibleCount > batchSize) {
				OutlinedButton(
					modifier = Modifier.height(24.dp),
					onClick = { visibleCount = batchSize },
					contentPadding = PaddingValues(
						top = 0.dp,
						bottom = 0.dp,
						start = 6.dp,
						end = 6.dp
					),
					colors = ButtonDefaults.outlinedButtonColors(
						contentColor = MaterialTheme.colorScheme.primary
					),
					border = BorderStroke(0.5.dp, DividerDefaults.color)
				) {
					Icon(
						imageVector = Icons.Default.KeyboardArrowUp,
						contentDescription = "Collapse",
						modifier = Modifier.size(20.dp)
					)
				}
				HorizontalDivider(
					modifier = Modifier
						.width(12.dp),
					thickness = 0.5.dp
				)
			}
			// Show More Button
			if (visibleCount < castList.size) {
				OutlinedButton(
					modifier = Modifier.height(24.dp),
					onClick = {
						visibleCount = (visibleCount + batchSize).coerceAtMost(castList.size)
					},
					contentPadding = PaddingValues(
						top = 0.dp,
						bottom = 0.dp,
						start = 12.dp,
						end = 6.dp
					),
					colors = ButtonDefaults.outlinedButtonColors(
						contentColor = MaterialTheme.colorScheme.primary
					),
					border = BorderStroke(0.5.dp, DividerDefaults.color)
				) {
					Text(
						text = "More",
						style = MaterialTheme.typography.labelMedium,
					)
					Icon(
						imageVector = Icons.Default.KeyboardArrowDown,
						contentDescription = "More",
						modifier = Modifier.size(20.dp)
					)
				}
			}
		} else {
			HorizontalDivider(
				modifier = Modifier
					.weight(1f),
				thickness = 0.5.dp
			)
		}
		HorizontalDivider(
			modifier = Modifier
				.weight(1f),
			thickness = 0.5.dp
		)
	}
}