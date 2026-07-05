package com.funapp.pookiemon.feature.pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.funapp.pookiemon.core.ui.components.rememberShimmerBrush
import com.funapp.pookiemon.core.ui.components.shimmerEffect

@Composable
fun DetailShimmer(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
                .padding(top = 24.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .shimmerEffect(brush, RoundedCornerShape(20.dp)),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(14.dp)
                        .shimmerEffect(brush, RoundedCornerShape(4.dp)),
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(28.dp)
                        .shimmerEffect(brush, RoundedCornerShape(6.dp)),
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(14.dp)
                        .shimmerEffect(brush, RoundedCornerShape(4.dp)),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .size(width = 60.dp, height = 28.dp)
                                .shimmerEffect(brush, RoundedCornerShape(14.dp)),
                        )
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

            ShimmerCard(brush = brush) {
                ShimmerTitle(brush)
                repeat(3) {
                    Box(modifier = Modifier.fillMaxWidth(0.75f).height(12.dp).shimmerEffect(brush, RoundedCornerShape(4.dp)))
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            ShimmerCard(brush = brush) {
                ShimmerTitle(brush)
                ShimmerLine(brush, 0.4f, 0.5f)
                Spacer(modifier = Modifier.height(4.dp))
                ShimmerLine(brush, 0.35f, 0.55f)
                Spacer(modifier = Modifier.height(4.dp))
                ShimmerLine(brush, 0.45f, 0.4f)
            }

            ShimmerCard(brush = brush) {
                ShimmerTitle(brush)
                repeat(6) {
                    ShimmerLine(brush, 0.5f, 0.3f)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            ShimmerCard(brush = brush) {
                ShimmerTitle(brush)
                ShimmerLine(brush, 0.4f, 0.5f)
            }
        }
    }
}

@Composable
private fun ShimmerTitle(brush: Brush) {
    Box(modifier = Modifier.fillMaxWidth(0.3f).height(16.dp).shimmerEffect(brush, RoundedCornerShape(4.dp)))
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun ShimmerLine(brush: Brush, labelWeight: Float, valueWeight: Float) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(labelWeight)
                .height(12.dp)
                .shimmerEffect(brush, RoundedCornerShape(4.dp)),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(valueWeight)
                .height(12.dp)
                .shimmerEffect(brush, RoundedCornerShape(4.dp)),
        )
    }
}

@Composable
private fun ShimmerCard(brush: Brush, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shimmerEffect(brush, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column { content() }
    }
}