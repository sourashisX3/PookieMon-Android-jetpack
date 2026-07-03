package com.funapp.pookiemon.feature.explore.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.funapp.pookiemon.R
import com.funapp.pookiemon.core.ui.components.AppTopBar
import com.funapp.pookiemon.core.utils.performClickHaptic

private data class ExploreCategory(
    val id: String,
    val labelRes: Int,
    val icon: ImageVector,
    val description: String,
)

private val categories = listOf(
    ExploreCategory("berries", R.string.explore_berries, Icons.Default.Eco, "View all Pokémon berries, their flavors, and firmness"),
    ExploreCategory("locations", R.string.explore_locations, Icons.Default.Explore, "Explore regions, locations, and areas"),
    ExploreCategory("evolution", R.string.explore_evolution, Icons.Default.Hub, "View evolution chains and triggers"),
    ExploreCategory("encounters", R.string.explore_encounters, Icons.Default.Link, "Browse encounter methods and conditions"),
    ExploreCategory("games", R.string.games_title, Icons.Default.Star, "Generations, versions, and Pokédexes"),
    ExploreCategory("references", R.string.references_title, Icons.Default.Favorite, "Abilities, types, and natures"),
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ExploreScreen(
    onCategoryClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.tab_explore),
            )
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(categories, key = { it.id }) { category ->
                ExploreCategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.id) },
                    modifier = Modifier.animateItem(),
                )
            }

            item {
                Box(modifier = Modifier.height(96.dp))
            }
        }
    }
}

@Composable
private fun ExploreCategoryCard(
    category: ExploreCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val view = LocalView.current
    Card(
        onClick = {
            view.performClickHaptic()
            onClick()
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                Color.Transparent,
                            ),
                        ),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.6f),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(category.labelRes),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = category.description,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
