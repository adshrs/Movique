package org.example.movique.ui.components.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowCircleRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import kotlinx.coroutines.NonCancellable.isActive
import org.example.movique.theme.extraColors
import org.example.movique.util.tools.Constants.NA
import kotlin.math.round

@Composable
fun MediaCard(
	cardId: Int,
	mediaTypeEnabled: Boolean = false,
	titleEnabled: Boolean = true,
	mediaType: String?,
	posterPath: String?,
	title: String?,
	voteAverage: Double?,
	releaseDate: String?,
	isActive: Boolean = false,
	onCardClick: (Int?) -> Unit = {},
	onAddActionClick: () -> Unit = {},
	onDetailActionClick: () -> Unit = {}
) {
	Box(
		modifier = Modifier
			.width(120.dp)
			.clip(RoundedCornerShape(12.dp))
			.border(
				border = BorderStroke(
					0.5.dp,
					MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
				),
				shape = RoundedCornerShape(12.dp)
			)
	) {
		OutlinedCard(
			modifier = Modifier
				.width(120.dp)
				.clickable(
					indication = null,
					interactionSource = null,
					onClick = { onCardClick(cardId) }
				),
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.surfaceContainer,
				contentColor = MaterialTheme.colorScheme.onSurface
			),
			border = BorderStroke(
				0.5.dp,
				MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
			)
		) {
			Column {
				Box(
					modifier = Modifier
						.fillMaxWidth()
						.aspectRatio(2f / 3f)
						.background(MaterialTheme.colorScheme.surfaceVariant),
					contentAlignment = Alignment.Center
				) {
					AsyncImage(
						model = ImageRequest.Builder(LocalPlatformContext.current)
							.data("https://image.tmdb.org/t/p/w500${posterPath}")
							.crossfade(true)
							.precision(Precision.INEXACT)
							.build(),
						contentDescription = title,
						modifier = Modifier
							.fillMaxSize(),
						contentScale = ContentScale.Crop,
					)
					if (mediaTypeEnabled) {
						Badge(
							modifier = Modifier.align(Alignment.TopStart).padding(6.dp),
							containerColor =
								if (mediaType == "movie")
									MaterialTheme.colorScheme.primaryContainer.copy(0.9f)
								else
									MaterialTheme.colorScheme.tertiaryContainer.copy(0.9f),
							contentColor =
								if (mediaType == "movie")
									Color.White.copy(0.8f)
								else
									Color.White.copy(0.8f)
						) {
							Text(
								text = when (mediaType) {
									"movie" -> "Movie"
									"tv" -> "TV Series"
									else -> NA
								},
								style = MaterialTheme.typography.labelMedium,
								modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
							)
						}
					}
				}

				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 8.dp, vertical = 8.dp),
					verticalArrangement = Arrangement.spacedBy(10.dp)
				) {
					if (titleEnabled) {
						Text(
							text = title ?: "No Title",
							style = MaterialTheme.typography.titleSmall,
							color = MaterialTheme.colorScheme.onSurface,
							minLines = 2,
							maxLines = 2,
							overflow = TextOverflow.Ellipsis
						)
					}
					Row(
						modifier = Modifier
							.fillMaxWidth(),
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
							modifier = Modifier.weight(1f),
							text = if (voteAverage != null) "${round(voteAverage * 10) / 10}" else NA,
							style = MaterialTheme.typography.labelMedium
						)
						Text(
							text = releaseDate?.take(4) ?: NA,
							style = MaterialTheme.typography.labelMedium,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
				}
			}
		}

		// Card action overlay
		Box(
			modifier = Modifier
				.matchParentSize()
				.background(
					color = Color.Black.copy(if (isActive) 0.3f else 0f),
					shape = RoundedCornerShape(12.dp)
				)
				.clip(RoundedCornerShape(12.dp))
				.clickable(
					indication = null,
					interactionSource = null,
					onClick = { onCardClick(cardId) }
				)
				.animateContentSize()
		)
		AnimatedVisibility(
			visible = isActive,
			enter = expandVertically(),
			exit = shrinkVertically()
		) {
			Box(
				modifier = Modifier
					.align(Alignment.TopCenter)
					.fillMaxWidth()
					.padding(8.dp)
					.background(
						color = MaterialTheme.colorScheme.surfaceContainer.copy(0.90f),
						shape = RoundedCornerShape(12.dp)
					)
					.clip(RoundedCornerShape(12.dp)),
				contentAlignment = Alignment.Center
			) {
				Row {
					Box(
						modifier = Modifier
							.weight(1f)
							.clickable(
								onClick = { onAddActionClick() }
							),
					) {
						Icon(
							modifier = Modifier.padding(vertical = 14.dp).align(Alignment.Center),
							imageVector = Icons.Rounded.Add,
							contentDescription = "Add Action",
							tint = MaterialTheme.colorScheme.primary
						)
					}
					Box(
						modifier = Modifier
							.weight(1f)
							.clickable(
								onClick = { onDetailActionClick() }
							),
					) {
						Icon(
							modifier = Modifier.padding(vertical = 14.dp).align(Alignment.Center),
							imageVector = Icons.Outlined.ArrowCircleRight,
							contentDescription = "Go to Details Action",
							tint = MaterialTheme.colorScheme.primary
						)
					}
				}
			}
		}
	}
}