@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.movique.ui

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ArraySerializer
import movique.composeapp.generated.resources.Res
import movique.composeapp.generated.resources.compose_multiplatform
import movique.composeapp.generated.resources.ic_theme
import org.example.movique.data.models.MovieResponseModel
import org.example.movique.theme.extraColors
import org.example.movique.util.Result
import org.example.movique.util.tools.Constants.NA
import org.example.movique.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.round

@Composable
fun HomeScreen(
	navController: NavHostController,
	innerPadding: PaddingValues,
	drawerState: DrawerState
) {
	val scope = rememberCoroutineScope()
	val homeViewModel = koinViewModel<HomeViewModel>()
	val getPopularMovies = homeViewModel.getPopularMovies.collectAsState()
	val popularMovies = (getPopularMovies.value as? Result.Success)?.data?.results ?: emptyList()
	val isLoading by homeViewModel.isLoading.collectAsState()

	LaunchedEffect(Unit) {
		homeViewModel.fetchPopularMovies()
	}

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		// Screen Content
		Scaffold {
			LazyColumn(
				modifier = Modifier.fillMaxSize()
			) {
				item { Spacer(modifier = Modifier.height(96.dp)) }
				item {
					Column(
						modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
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
								text = "Popular Movies",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Bold,
								color = MaterialTheme.colorScheme.onSurface
							)
							TextButton(
								modifier = Modifier.height(30.dp),
								onClick = { },
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
						if (isLoading || popularMovies.isEmpty()) {
							ShimmerLoadingRow()
						} else {
							LazyRow(
								modifier = Modifier.fillMaxSize(),
							) {
								item { Spacer(modifier = Modifier.width(16.dp)) }
								items(popularMovies.size) { index ->
									MovieCard(movie = popularMovies[index])
									if (index < popularMovies.size - 1) {
										Spacer(modifier = Modifier.width(8.dp))
									}
								}
								item { Spacer(modifier = Modifier.width(16.dp)) }
							}
						}
					}
				}
				item {
					Column(
						modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
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
								text = "Popular Movies",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Bold,
								color = MaterialTheme.colorScheme.onSurface
							)
							TextButton(
								modifier = Modifier.height(30.dp),
								onClick = { },
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
						if (isLoading || popularMovies.isEmpty()) {
							ShimmerLoadingRow()
						} else {
							LazyRow(
								modifier = Modifier.fillMaxSize(),
							) {
								item { Spacer(modifier = Modifier.width(16.dp)) }
								items(popularMovies.size) { index ->
									MovieCard(movie = popularMovies[index])
									if (index < popularMovies.size - 1) {
										Spacer(modifier = Modifier.width(8.dp))
									}
								}
								item { Spacer(modifier = Modifier.width(16.dp)) }
							}
						}
					}
				}
				item {
					Column(
						modifier = Modifier.fillMaxWidth(),
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
								text = "Popular Movies",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.Bold,
								color = MaterialTheme.colorScheme.onSurface
							)
							TextButton(
								modifier = Modifier.height(30.dp),
								onClick = { },
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
						if (isLoading || popularMovies.isEmpty()) {
							ShimmerLoadingRow()
						} else {
							LazyRow(
								modifier = Modifier.fillMaxSize(),
							) {
								item { Spacer(modifier = Modifier.width(16.dp)) }
								items(popularMovies.size) { index ->
									MovieCard(movie = popularMovies[index])
									if (index < popularMovies.size - 1) {
										Spacer(modifier = Modifier.width(8.dp))
									}
								}
								item { Spacer(modifier = Modifier.width(16.dp)) }
							}
						}
					}
				}
				item { Spacer(modifier = Modifier.height(84.dp)) }
			}
		}

		// Top Bar
		CenterAlignedTopAppBar(
			modifier = Modifier
				.height(80.dp)
				.background(
					Brush.verticalGradient(
						colors = listOf(
							BottomAppBarDefaults.containerColor,
							BottomAppBarDefaults.containerColor.copy(0.9f)
						)
					)
				),
			title = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Movique",
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurface,
						textAlign = TextAlign.Center
					)
				}
			},
			navigationIcon = {
				Box(
					modifier = Modifier
						.fillMaxHeight(),
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = { scope.launch { drawerState.open() } }) {
						Icon(
							imageVector = Icons.Outlined.Menu,
							contentDescription = "Menu",
							tint = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = Color.Transparent,
				titleContentColor = MaterialTheme.colorScheme.onSurface,
				navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
				actionIconContentColor = MaterialTheme.colorScheme.onSurface
			)
		)
	}
}

@Composable
fun MovieCard(movie: MovieResponseModel.Movie) {
	OutlinedCard(
		modifier = Modifier.width(120.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainer,
			contentColor = MaterialTheme.colorScheme.onSurface
		),
		border = BorderStroke(
			0.5.dp,
			MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
		),
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
						.data("https://image.tmdb.org/t/p/w500${movie.posterPath}")
						.crossfade(true)
						.precision(Precision.INEXACT)
						.build(),
					contentDescription = movie.title,
					modifier = Modifier
						.fillMaxSize(),
					contentScale = ContentScale.Crop,
				)
			}

			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 8.dp, vertical = 8.dp),
				verticalArrangement = Arrangement.spacedBy(10.dp)
			) {
				Text(
					text = movie.title ?: "No Title",
					style = MaterialTheme.typography.titleSmall,
					color = MaterialTheme.colorScheme.onSurface,
					minLines = 2,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis
				)
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
						text = if (movie.voteAverage != null) "${round(movie.voteAverage * 10) / 10}" else NA,
						style = MaterialTheme.typography.labelMedium
					)
					Text(
						text = movie.releaseDate?.take(4) ?: NA,
						style = MaterialTheme.typography.labelMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}
		}
	}
}

@Composable
fun ShimmerLoadingRow() {
	val shimmerColors = listOf(
		Color.Gray.copy(alpha = 0.6f),
		Color.Gray.copy(alpha = 0.2f),
		Color.Gray.copy(alpha = 0.6f)
	)
	val transition = rememberInfiniteTransition()
	val translateAnim by transition.animateFloat(
		initialValue = 0f,
		targetValue = 1000f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 1200,
				easing = LinearEasing
			),
			repeatMode = RepeatMode.Restart
		)
	)

	LazyRow(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(horizontal = 16.dp)
	) {
		items(5) {
			ShimmerCard(
				modifier = Modifier
					.width(128.dp)
					.padding(end = 8.dp),
				translateAnim = translateAnim,
				shimmerColors = shimmerColors
			)
		}
	}
}

@Composable
fun ShimmerCard(
	modifier: Modifier = Modifier,
	translateAnim: Float = 0f,
	shimmerColors: List<Color> = listOf(
		Color(0xFFE0E0E0).copy(alpha = 0.3f), // Subtle light gray
		Color(0xFFE0E0E0).copy(alpha = 0.1f), // Softer highlight
		Color(0xFFE0E0E0).copy(alpha = 0.3f)  // Back to base
	)
) {
	// Pulse animation for subtle scaling effect
	val pulseAnim by rememberInfiniteTransition().animateFloat(
		initialValue = 0.99f,
		targetValue = 1.01f,
		animationSpec = infiniteRepeatable(
			animation = tween(
				durationMillis = 800,
				easing = EaseInOutQuad
			),
			repeatMode = RepeatMode.Reverse
		)
	)

	// Gradient brush for shimmer effect
	val brush = Brush.linearGradient(
		colors = shimmerColors,
		start = Offset(translateAnim - 300f, 0f), // Wider gradient for smoother effect
		end = Offset(translateAnim, 0f)
	)

	OutlinedCard(
		modifier = modifier
			.width(120.dp) // Exact width as MovieCard
		, // Subtle pulse effect
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainer,
			contentColor = MaterialTheme.colorScheme.onSurface
		),
		border = BorderStroke(
			0.5.dp,
			MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
		) // Same border as MovieCard
	) {
		Column {
			// Poster placeholder (matches MovieCard's aspect ratio)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(2f / 3f) // Exact 2:3 ratio as MovieCard
					.background(brush)
					.clip(MaterialTheme.shapes.medium) // Rounded corners for polish
			)

			// Text area (matches MovieCard's padding and spacing)
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 8.dp, vertical = 8.dp), // Exact padding as MovieCard
				verticalArrangement = Arrangement.spacedBy(10.dp) // Exact spacing as MovieCard
			) {
				// Title placeholder (matches title text size and height)
				Column {
					Box(
						modifier = Modifier
							.fillMaxWidth() // Slightly shorter to mimic title variation
							.height(16.dp) // Approximates titleSmall with 2 lines
							.background(brush, RoundedCornerShape(8.dp))

					)
					Spacer(modifier = Modifier.height(4.dp))
					Box(
						modifier = Modifier
							.fillMaxWidth() // Slightly shorter to mimic title variation
							.height(16.dp) // Approximates titleSmall with 2 lines
							.background(brush, RoundedCornerShape(8.dp))
					)
				}
				// Rating placeholder (matches rating row size)
				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Row {
						Box(
							modifier = Modifier
								.size(14.dp) // Matches Star icon size
								.background(brush, RoundedCornerShape(8.dp))

						)
						Spacer(modifier = Modifier.width(4.dp)) // Mimic icon-text gap
						Box(
							modifier = Modifier
								.width(32.dp) // Approximates rating text width
								.height(14.dp) // Matches bodySmall
								.background(brush, RoundedCornerShape(8.dp))
						)
					}
					Box(
						modifier = Modifier
							.width(40.dp) // Approximates rating text width
							.height(14.dp) // Matches bodySmall
							.background(brush, RoundedCornerShape(8.dp))
					)
				}
			}
		}
	}
}