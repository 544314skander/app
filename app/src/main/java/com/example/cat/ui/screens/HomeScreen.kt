@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cat.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.cat.data.Cat
import com.example.cat.ui.CatUiState
import com.example.cat.ui.theme.Amber
import com.example.cat.ui.theme.DeepBlue
import com.example.cat.ui.theme.Mist
import com.example.cat.ui.theme.Teal

@Composable
fun HomeScreen(
    state: CatUiState,
    onCatSelected: (Int) -> Unit,
    onOpenTools: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onToggleFilter: () -> Unit
) {
    val visibleCats = state.visibleCats
    val favorites = state.cats.count { it.isFavorite }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Cat Lounge") },
                actions = {
                    IconButton(onClick = onOpenTools) {
                        Icon(
                            imageVector = Icons.Rounded.Map,
                            contentDescription = "Open tools"
                        )
                    }
                    IconButton(onClick = onToggleFilter) {
                        Icon(
                            imageVector = Icons.Outlined.FilterAlt,
                            contentDescription = "Toggle favorite cats",
                            tint = if (state.showOnlyFavorites) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            MissionSummaryRow(total = state.cats.size, favorites = favorites)
            if (visibleCats.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(visibleCats) { cat ->
                        CatCard(
                            cat = cat,
                            onClick = { onCatSelected(cat.id) },
                            onToggleFavorite = { onToggleFavorite(cat.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MissionSummaryRow(total: Int, favorites: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SummaryBox(
            label = "Cats Lounging",
            value = "$total",
            background = Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, Teal)),
            modifier = Modifier.weight(1f)
        )
        SummaryBox(
            label = "Favorites",
            value = "$favorites",
            background = Brush.linearGradient(listOf(MaterialTheme.colorScheme.tertiary, Amber)),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryBox(label: String, value: String, background: Brush, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(90.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .padding(14.dp)
    ) {
        Column {
            Text(text = label, color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = value, color = Color.White, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
private fun CatCard(
    cat: Cat,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.85f), DeepBlue),
                            radius = 70f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.RocketLaunch,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cat.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = cat.model,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                StatusPill(text = cat.status)
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (cat.isFavorite) Icons.Outlined.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Toggle favorite",
                    tint = if (cat.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun StatusPill(text: String) {
    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Mist),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.RocketLaunch,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text("No cats match this filter", style = MaterialTheme.typography.titleMedium)
        Text(
            "Turn off favorites filter or add more cats.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}
