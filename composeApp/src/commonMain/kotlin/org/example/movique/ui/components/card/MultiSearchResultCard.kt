package org.example.movique.ui.components.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import org.example.movique.data.models.search.MultiSearchResponseModel
import org.example.movique.theme.extraColors
import org.example.movique.ui.components.badge.MediaTypeBadge
import org.example.movique.util.tools.Constants.NA
import kotlin.math.round

@Composable
fun MultiSearchResultCard(
	cardId: Int,
	result: MultiSearchResponseModel.MultiResult,
	isActive: Boolean = false,
	onCardClick: (Int?) -> Unit = {},
	onAddActionClick: () -> Unit = {},
	onDetailActionClick: () -> Unit = {}
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(IntrinsicSize.Min)
			.padding(vertical = 4.dp)
	) {
		OutlinedCard(
			modifier = Modifier
				.weight(1f)
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
			),
			shape = RoundedCornerShape(16.dp)
		) {
			Row(
				modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
			) {
				// Poster on the left
				Box(
					modifier = Modifier
						.padding(6.dp)
						.width(64.dp)
						.aspectRatio(2f / 3f)
						.background(
							MaterialTheme.colorScheme.surfaceVariant,
							shape = RoundedCornerShape(12.dp)
						)
						.clip(shape = RoundedCornerShape(12.dp)),
					contentAlignment = Alignment.Center
				) {
					AsyncImage(
						model = ImageRequest.Builder(LocalPlatformContext.current)
							.data("https://image.tmdb.org/t/p/w500${result.posterPath}")
							.crossfade(true)
							.precision(Precision.INEXACT)
							.build(),
						contentDescription = result.title ?: result.name,
						modifier = Modifier.fillMaxSize(),
						contentScale = ContentScale.Crop
					)
				}
				// Information on the right
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.padding(vertical = 10.dp)
						.padding(start = 8.dp, end = 10.dp)
				) {
					Text(
						text = (if (result.mediaType == "movie") result.title else result.name)
							?: "No Title",
						style = MaterialTheme.typography.titleSmall,
						color = MaterialTheme.colorScheme.onSurface,
						minLines = 1,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)
					Spacer(modifier = Modifier.height(4.dp))
					Row(
						modifier = Modifier.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(
							modifier = Modifier,
							text = (if (result.mediaType == "movie") result.releaseDate else result.firstAirDate)?.take(
								4
							) ?: NA,
							style = MaterialTheme.typography.labelMedium,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
					Spacer(modifier = Modifier.weight(1f))
					Row(
						modifier = Modifier.fillMaxWidth(),
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
							text = if (result.voteAverage != null) "${round(result.voteAverage * 10) / 10}" else NA,
							style = MaterialTheme.typography.labelMedium
						)
						Spacer(modifier = Modifier.weight(1f))
						MediaTypeBadge(
							modifier = Modifier,
							mediaType = result.mediaType
						)
					}
				}
			}
		}
		AnimatedVisibility(
			visible = isActive,
			enter = expandHorizontally(),
			exit = shrinkHorizontally()
		) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Spacer(modifier = Modifier.width(8.dp))
				OutlinedCard(
					modifier = Modifier.fillMaxHeight().wrapContentWidth(),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.surfaceContainer,
						contentColor = MaterialTheme.colorScheme.onSurface
					),
					border = BorderStroke(
						0.5.dp,
						MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
					),
					shape = RoundedCornerShape(16.dp)
				) {
					Column {
						Box(
							modifier = Modifier
								.weight(1f)
								.clickable(
									onClick = { onAddActionClick() }
								),
						) {
							Icon(
								modifier = Modifier.padding(horizontal = 14.dp).align(Alignment.Center),
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
								modifier = Modifier.padding(horizontal = 14.dp).align(Alignment.Center),
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
}