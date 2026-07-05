package com.funapp.pookiemon.feature.pokemon.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.funapp.pookiemon.core.ui.components.AppErrorView
import com.funapp.pookiemon.core.ui.components.AppTopBar
import com.funapp.pookiemon.feature.pokemon.domain.model.Pokemon
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonSpecies
import com.funapp.pookiemon.feature.pokemon.presentation.components.DetailShimmer
import com.funapp.pookiemon.feature.pokemon.presentation.components.InfoRow
import com.funapp.pookiemon.feature.pokemon.presentation.components.StatSection
import com.funapp.pookiemon.feature.pokemon.presentation.components.TypeChip
import com.funapp.pookiemon.feature.pokemon.presentation.components.typeColor
import com.funapp.pookiemon.feature.pokemon.presentation.events.DetailUiEvent
import com.funapp.pookiemon.feature.pokemon.presentation.viewmodels.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onNavigateBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopBar(
                title = uiState.pokemon?.name?.replaceFirstChar { it.uppercase() } ?: stringResource(com.funapp.pookiemon.R.string.pokemon_default),
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onNavigateBack,
            )
        },
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = uiState.isLoading,
            onRefresh = { viewModel.onEvent(DetailUiEvent.RetryClicked) },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            when {
                uiState.isLoading -> {
                    DetailShimmer(modifier = Modifier.fillMaxSize())
                }
                uiState.error != null -> {
                    AppErrorView(
                        message = uiState.error ?: stringResource(com.funapp.pookiemon.R.string.unknown_error),
                        onRetry = { viewModel.onEvent(DetailUiEvent.RetryClicked) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                uiState.pokemon != null -> {
                    PokemonDetailContent(
                        pokemon = uiState.pokemon!!,
                        species = uiState.species,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PokemonDetailContent(
    pokemon: Pokemon,
    species: PokemonSpecies?,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    val primaryType = pokemon.types.firstOrNull()?.name ?: "normal"
    val typeColorValue = typeColor(primaryType)
    val headerGradient = Brush.verticalGradient(
        colors = listOf(
            typeColorValue.copy(alpha = 0.2f),
            typeColorValue.copy(alpha = 0.05f),
            MaterialTheme.colorScheme.surface,
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerGradient)
                .padding(top = 16.dp, bottom = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val bannerModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                    with(sharedTransitionScope) {
                        Modifier
                            .size(200.dp)
                            .sharedElement(
                                sharedContentState = rememberSharedContentState(key = "pokemon_image_${pokemon.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ -> tween(durationMillis = 350) },
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                    }
                } else {
                    Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                }

                Box(
                    modifier = bannerModifier,
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = pokemon.sprites?.artworkDefault
                            ?: pokemon.sprites?.frontDefault,
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Fit,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                if (species?.genus != null) {
                    Text(
                        text = species.genus,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    pokemon.types.forEach { type ->
                        TypeChip(typeName = type.name)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .background(typeColorValue, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                    ) {
                        Text(
                            text = "#${pokemon.id.toString().padStart(4, '0')}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            color = Color.White,
                        )
                    }

                    species?.color?.let { colorName ->
                        val chipColor = colorFromName(colorName)
                        Box(
                            modifier = Modifier
                                .background(
                                    chipColor.copy(alpha = 0.12f),
                                    RoundedCornerShape(8.dp),
                                )
                                .then(
                                    Modifier.border(
                                        BorderStroke(1.dp, chipColor.copy(alpha = 0.5f)),
                                        RoundedCornerShape(8.dp),
                                    )
                                )
                                .padding(horizontal = 10.dp, vertical = 3.dp),
                        ) {
                            Text(
                                text = colorName.replaceFirstChar { c -> c.uppercase() },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ),
                                color = chipColor,
                            )
                        }
                    }
                }

                if (species != null && (species.isLegendary || species.isMythical || species.isBaby)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (species.isLegendary) {
                            BadgeChip(
                                icon = Icons.Default.AutoAwesome,
                                text = stringResource(com.funapp.pookiemon.R.string.legendary),
                                tint = colorResource(com.funapp.pookiemon.R.color.legendary_gold),
                            )
                        }
                        if (species.isMythical) {
                            BadgeChip(
                                icon = Icons.Default.AutoAwesome,
                                text = stringResource(com.funapp.pookiemon.R.string.mythical),
                                tint = colorResource(com.funapp.pookiemon.R.color.mythical_pink),
                            )
                        }
                        if (species.isBaby) {
                            BadgeChip(
                                icon = Icons.Default.Star,
                                text = stringResource(com.funapp.pookiemon.R.string.baby),
                                tint = colorResource(com.funapp.pookiemon.R.color.legendary_gold),
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            AnimatedSection(index = 0) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        val heightM = pokemon.height / 10.0
                        val weightKg = pokemon.weight / 10.0
                        StatRow(label = stringResource(com.funapp.pookiemon.R.string.height), value = stringResource(com.funapp.pookiemon.R.string.meters_format, heightM))
                        StatRow(label = stringResource(com.funapp.pookiemon.R.string.weight), value = stringResource(com.funapp.pookiemon.R.string.kg_format, weightKg))
                        StatRow(
                            label = stringResource(com.funapp.pookiemon.R.string.base_xp),
                            value = pokemon.baseExperience?.toString() ?: "\u2014",
                        )
                    }
                }
            }

            if (pokemon.abilities.isNotEmpty()) {
                AnimatedSection(index = 1) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(com.funapp.pookiemon.R.string.abilities),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                pokemon.abilities.forEach { ability ->
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (ability.isHidden) typeColorValue.copy(alpha = 0.12f)
                                                else MaterialTheme.colorScheme.surfaceVariant,
                                                RoundedCornerShape(8.dp),
                                            )
                                            .padding(horizontal = 10.dp, vertical = 5.dp),
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        ) {
                                            if (ability.isHidden) {
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(12.dp),
                                                    tint = typeColorValue,
                                                )
                                            }
                                            Text(
                                                text = ability.name
                                                    .replaceFirstChar { c -> c.uppercase() }
                                                    .replace("-", " "),
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    fontWeight = FontWeight.SemiBold,
                                                ),
                                                color = if (ability.isHidden) typeColorValue
                                                else MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pokemon.stats.isNotEmpty()) {
                AnimatedSection(index = 2) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            StatSection(
                                stats = pokemon.stats,
                                accentColor = typeColorValue,
                            )
                        }
                    }
                }
            }

            if (species != null) {
                if (species.flavorText != null) {
                    AnimatedSection(index = 3) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = stringResource(com.funapp.pookiemon.R.string.description),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = species.flavorText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }

                AnimatedSection(index = 4) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(com.funapp.pookiemon.R.string.details),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        species.habitat?.let {
                            InfoRow(label = stringResource(com.funapp.pookiemon.R.string.habitat), value = it.replaceFirstChar { c -> c.uppercase() })
                        }
                        if (species.eggGroups.isNotEmpty()) {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.egg_groups),
                                value = species.eggGroups.joinToString(", ") { it.replaceFirstChar { c -> c.uppercase() } },
                            )
                        }
                        species.generation?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.generation),
                                value = it.replaceFirstChar { c -> c.uppercase() }.replace("-", " "),
                            )
                        }
                        species.growthRate?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.growth_rate),
                                value = it.replaceFirstChar { c -> c.uppercase() }.replace("-", " "),
                            )
                        }
                        species.shape?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.shape),
                                value = it.replaceFirstChar { c -> c.uppercase() },
                            )
                        }
                        species.captureRate?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.capture_rate),
                                value = it.toString(),
                            )
                        }
                        species.baseHappiness?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.base_happiness),
                                value = it.toString(),
                            )
                        }
                        species.hatchCounter?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.hatch_counter),
                                value = it.toString(),
                            )
                        }
                        species.evolvesFromSpecies?.let {
                            InfoRow(
                                label = stringResource(com.funapp.pookiemon.R.string.evolves_from),
                                value = it.replaceFirstChar { c -> c.uppercase() }.replace("-", " "),
                            )
                        }
                        InfoRow(
                            label = stringResource(com.funapp.pookiemon.R.string.gender_differences),
                            value = if (species.hasGenderDifferences) stringResource(com.funapp.pookiemon.R.string.yes) else stringResource(com.funapp.pookiemon.R.string.no),
                        )
                    }
                }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun colorFromName(name: String): Color = when (name.lowercase()) {
    "black" -> Color.Black
    "blue" -> Color(0xFF3B82F6)
    "brown" -> Color(0xFF8B4513)
    "gray" -> Color.Gray
    "green" -> Color(0xFF22C55E)
    "pink" -> Color(0xFFEC4899)
    "purple" -> Color(0xFFA855F7)
    "red" -> Color(0xFFEF4444)
    "white" -> Color(0xFF9CA3AF)
    "yellow" -> Color(0xFFEAB308)
    else -> Color.Gray
}

@Composable
private fun AnimatedSection(
    index: Int,
    content: @Composable () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }
    val baseDelay = 300
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(delayMillis = baseDelay + index * 100, durationMillis = 400)) +
            slideInVertically(
                animationSpec = tween(delayMillis = baseDelay + index * 100, durationMillis = 400),
                initialOffsetY = { it / 4 },
            ),
    ) {
        content()
    }
}

@Composable
private fun BadgeChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(14.dp),
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = tint,
        )
    }
}

@Composable
private fun StatRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
