@file:OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)

package org.example.movique.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.More
import androidx.compose.material.icons.outlined.MoreTime
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreTime
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.example.movique.MediaDetailsScreen
import org.example.movique.data.models.mediadetails.MovieDetailsResponseModel
import org.example.movique.data.models.mediadetails.TvSeriesDetailsResponseModel
import org.example.movique.theme.extraColors
import org.example.movique.theme.isSystemInDarkTheme
import org.example.movique.theme.titleRegular
import org.example.movique.ui.components.badge.MediaTypeBadge
import org.example.movique.ui.components.card.MediaCard
import org.example.movique.ui.components.chip.GenreChip
import org.example.movique.ui.components.mediadetailssection.MediaBackdrop
import org.example.movique.ui.components.mediadetailssection.MediaMainInfoSection
import org.example.movique.ui.components.tab.NoRippleTab
import org.example.movique.util.Result
import org.example.movique.util.Result.Loading.isLoading
import org.example.movique.util.tools.Constants.NA
import org.example.movique.util.tools.smoothCinematicVerticalGradientBrush
import org.example.movique.util.tools.toHourMinuteFormat
import org.example.movique.util.tools.toSeasonText
import org.example.movique.viewmodel.MediaDetailsViewModel
import org.example.movique.viewmodel.MediaListViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.PI
import kotlin.math.round

@Composable
fun MediaDetailsScreen(
	navController: NavHostController,
	mediaId: Int,
	mediaType: String
) {
	val mediaDetailsViewModel = koinViewModel<MediaDetailsViewModel>()
	val isLoading by mediaDetailsViewModel.isLoading.collectAsState()
	val getMovieDetails = mediaDetailsViewModel.getMovieDetails.collectAsState()
	val getTvSeriesDetails = mediaDetailsViewModel.getTvSeriesDetails.collectAsState()
	val movie = (getMovieDetails.value as? Result.Success)?.data
	val tvSeries = (getTvSeriesDetails.value as? Result.Success)?.data
	val listState = rememberScrollState()
	val isScrolling = listState.isScrollInProgress
	val scope = rememberCoroutineScope()
	val localDensity = LocalDensity.current

	val topBarHeightPx = with(localDensity) { 20.dp.toPx() }
	val statusBarPx = with(localDensity) { WindowInsets.statusBars.getTop(localDensity).toFloat() }
	val topTriggerPx = statusBarPx + topBarHeightPx

	var titleTopPx by remember { mutableStateOf(Float.POSITIVE_INFINITY) } // measured in window co-ordinates
	var showTopBar by remember { mutableStateOf(false) }

	// Show Top Bar Logic (Show only when scroll reaches the media title text)
	LaunchedEffect(listState) {
		snapshotFlow { titleTopPx to listState.value }
			.map { (titleY, _) ->
				// When title's top is at or above the trigger Y, show top bar
				// add small fudge (e.g. 2 px) if needed
				titleY.isFinite() && titleY <= topTriggerPx + 2f
			}
			.distinctUntilChanged()
			.collect { showTopBar = it }
	}

	// Initial fetch
	LaunchedEffect(mediaType) {
		when (mediaType) {
			"movie" -> {
				if (movie?.id != mediaId) {
					mediaDetailsViewModel.fetchMovieDetails(mediaId)
				}
			}

			"tv" -> {
				if (tvSeries?.id != mediaId) {
					mediaDetailsViewModel.fetchTvSeriesDetails(mediaId)
				}
			}
		}
	}

	Box(Modifier.fillMaxSize()) {
		if (isLoading) {
			// Loading Indicator
			Scaffold(
				modifier = Modifier.fillMaxSize()
			) {
				Box(
					modifier = Modifier.fillMaxSize()
				) {
					CircularProgressIndicator(
						modifier = Modifier.size(20.dp).align(Alignment.Center),
						color = MaterialTheme.colorScheme.primary
					)
				}
			}
		} else {
			// Screen Content
			Scaffold {
				Box(
					modifier = Modifier.fillMaxSize()
				) {
					// Backdrop
					MediaBackdrop(mediaType, movie, tvSeries)
					Box(
						modifier = Modifier
							.fillMaxSize()
							.verticalScroll(listState)
					) {
						// Backdrop gradient overlay
						Column(modifier = Modifier.matchParentSize()) {
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.aspectRatio(16f / 9f)
									.background(
										smoothCinematicVerticalGradientBrush(
											baseColor = MaterialTheme.colorScheme.background,
											topAlpha = 0.2f,
											midAlpha = 0.1f,
											bottomAlpha = 1f,
											midPosition = 0.35f,
											steps = 500
										)
									)
							)
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.weight(1f)
									.background(MaterialTheme.colorScheme.background)
							)
						}

						// Media Info
						Column(
							modifier = Modifier.fillMaxWidth()
						) {
							Spacer(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()))
							Spacer(modifier = Modifier.padding(top = 104.dp))
							// Main Info (including Poster, Tagline & Overview)
							MediaMainInfoSection(
								modifier = Modifier,
								mediaType = mediaType,
								movie = movie,
								tvSeries = tvSeries,
								retrieveTitleTopPx = { titleTopPx = it }
							)

							Spacer(modifier = Modifier.height(12.dp))

							// For Selectable Tabs
							val tabs = listOf(
								"Cast",
								"Crew",
								"Genres",
								"Details"
							)
							var selectedTabIndex by remember { mutableStateOf(0) }

							TabRow(
								modifier = Modifier,
								selectedTabIndex = selectedTabIndex,
								containerColor = Color.Transparent,
								divider = {
									HorizontalDivider(
										modifier = Modifier
											.fillMaxWidth()
											.height(0.5.dp)
									)
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

							Spacer(Modifier.height(1000.dp))
							Spacer(Modifier.height(76.dp))
							Spacer(modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))
						}
					}

					// Bottom Action Bar
					Box(
						modifier = Modifier
							.align(Alignment.BottomCenter)
							.padding(WindowInsets.navigationBars.asPaddingValues())
					) {
						AnimatedVisibility(
							visible = !isScrolling,
							enter = fadeIn() + slideInVertically(
								initialOffsetY = { 120 },
							),
							exit = fadeOut() + slideOutVertically(
								targetOffsetY = { 120 },
							)
						) {
							Card(
								modifier = Modifier
									.padding(vertical = 4.dp),
								border = BorderStroke(
									0.5.dp,
									MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
								),
								shape = MaterialTheme.shapes.extraLarge,
								colors = CardDefaults.cardColors(
									containerColor = BottomAppBarDefaults.containerColor.copy(0.85f)
								)
							) {
								Row(
									modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
									verticalAlignment = Alignment.CenterVertically,
									horizontalArrangement = Arrangement.spacedBy(24 .dp)
								) {
									Icon(
										imageVector = Icons.Rounded.FavoriteBorder,
										contentDescription = "Like",
										modifier = Modifier
											.size(32.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
									Icon(
										imageVector = Icons.Rounded.StarOutline,
										contentDescription = "Rate",
										modifier = Modifier
											.size(38.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
									Icon(
										imageVector = Icons.Outlined.Visibility,
										contentDescription = "Watch",
										modifier = Modifier
											.size(36.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
									Icon(
										imageVector = Icons.Rounded.MoreTime,
										contentDescription = "Watchlist",
										modifier = Modifier
											.size(34.dp)
											.clickable(
												interactionSource = remember { MutableInteractionSource() },
												indication = null,
												onClick = { }
											),
										tint = MaterialTheme.colorScheme.primary
									)
								}
							}
						}
					}
				}
			}

			// Static Top Bar (Only Icon Buttons)
			TopAppBar(
				modifier = Modifier
					.height(80.dp)
					.background(Color.Transparent),
				title = { },
				navigationIcon = {
					Box(
						modifier = Modifier.fillMaxHeight(),
						contentAlignment = Alignment.Center
					) {
						IconButton(
							onClick = { navController.popBackStack() },
							colors = IconButtonDefaults.iconButtonColors(
								containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(0.4f),
							)
						) {
							Icon(
								imageVector = Icons.AutoMirrored.Filled.ArrowBack,
								contentDescription = "Back",
								tint = MaterialTheme.colorScheme.onSurface
							)
						}
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent
				)
			)

			// Animated Top Bar (Icon Buttons + Title)
			AnimatedVisibility(
				visible = showTopBar,
				enter = fadeIn(
					animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing)
				) + slideInVertically(
					initialOffsetY = { -80 }, // start slightly above the screen
					animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
				),
				exit = fadeOut(
					animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
				) + slideOutVertically(
					targetOffsetY = { -80 }, // slide smoothly upward when disappearing
					animationSpec = tween(durationMillis = 400, easing = FastOutLinearInEasing)
				)
			) {
				TopAppBar(
					modifier = Modifier
						.height(80.dp)
						.background(
							Brush.verticalGradient(
								listOf(
									BottomAppBarDefaults.containerColor,
									BottomAppBarDefaults.containerColor.copy(0.9f)
								)
							)
						),
					title = {
						Box(
							modifier = Modifier.fillMaxHeight(),
							contentAlignment = Alignment.Center
						) {
							Text(
								text = when (mediaType) {
									"movie" -> movie?.title ?: "Movie Title"
									"tv" -> tvSeries?.name ?: "Tv Series Title"
									else -> ""
								},
								style = MaterialTheme.typography.titleRegular,
								color = MaterialTheme.colorScheme.onSurface,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}
					},
					navigationIcon = {
						Box(
							modifier = Modifier.fillMaxHeight(),
							contentAlignment = Alignment.Center
						) {
							IconButton(onClick = { navController.popBackStack() }) {
								Icon(
									imageVector = Icons.AutoMirrored.Filled.ArrowBack,
									contentDescription = "Back",
									tint = MaterialTheme.colorScheme.onSurface
								)
							}
						}
					},
					colors = TopAppBarDefaults.topAppBarColors(
						containerColor = Color.Transparent
					)
				)
			}
		}
	}
}