package com.example.karunada_vanya.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karunada_vanya.data.model.WikiItem
import com.example.karunada_vanya.data.repository.WikiRepository
import com.example.karunada_vanya.ui.theme.*

@Composable
fun WikiScreen(modifier: Modifier = Modifier) {
    var selectedCategory by remember { mutableStateOf("ALL") }
    var selectedItem by remember { mutableStateOf<WikiItem?>(null) }

    val categories = listOf("ALL", "ANIMAL", "BIRD", "FLORA")
    val items = remember(selectedCategory) {
        WikiRepository.getItemsByCategory(selectedCategory)
    }

    if (selectedItem != null) {
        WikiDetailScreen(
            item = selectedItem!!,
            onBack = { selectedItem = null },
            modifier = modifier
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamWhite)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
            Text(
                text = "Wildlife",
                style = MaterialTheme.typography.displayMedium,
                color = TextDark
            )
            Text(
                text = "Collection",
                style = MaterialTheme.typography.displayMedium.copy(fontStyle = FontStyle.Italic),
                color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Stories of the forest, curated by Gemini AI.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Filter chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(categories.size) { index ->
                    val cat = categories[index]
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isSelected) ForestGreen else CardWhite)
                            .border(1.dp, if (isSelected) ForestGreen else DividerColor, RoundedCornerShape(24.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) CardWhite else TextDark
                        )
                    }
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                WikiCard(item = item, onClick = { selectedItem = item })
            }
        }
    }
}

@Composable
fun WikiCard(item: WikiItem, onClick: () -> Unit) {
    val categoryColor = when (item.category) {
        "ANIMAL" -> WarmOrange
        "BIRD" -> androidx.compose.ui.graphics.Color(0xFF3B82F6)
        "FLORA" -> androidx.compose.ui.graphics.Color(0xFF22C55E)
        else -> TextMuted
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        // Background gradient (simulating image)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ForestGreen.copy(alpha = 0.6f),
                            ForestGreenDark
                        )
                    )
                )
        )

        // Animal emoji as placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(getAnimalGradient(item.category)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getAnimalEmoji(item.name),
                fontSize = 60.sp
            )
        }

        // Overlay gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            androidx.compose.ui.graphics.Color.Transparent,
                            androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.75f)
                        ),
                        startY = 80f
                    )
                )
        )

        // Category label and name at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Text(
                text = item.category,
                style = MaterialTheme.typography.labelSmall,
                color = categoryColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp),
                color = CardWhite
            )
        }
    }
}

@Composable
fun WikiDetailScreen(item: WikiItem, onBack: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamWhite)
            .verticalScroll(rememberScrollState())
    ) {
        // Header image area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(ForestGreen, ForestGreenDark)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = getAnimalEmoji(item.name), fontSize = 100.sp)

            // Back button
            TextButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Text("← BACK", style = MaterialTheme.typography.labelMedium, color = CardWhite)
            }

            // Bottom info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = WarmOrange
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.displayMedium,
                    color = CardWhite
                )
                Text(
                    text = item.region.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = CardWhite.copy(alpha = 0.6f)
                )
            }
        }

        // Content
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "CONSERVATION STATUS",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Text(
                        text = item.conservationStatus,
                        style = MaterialTheme.typography.titleMedium,
                        color = WarmOrange,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "REGION",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Text(
                        text = item.region,
                        style = MaterialTheme.typography.titleMedium,
                        color = ForestGreen,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Divider(color = DividerColor, modifier = Modifier.padding(vertical = 20.dp))

            Text(
                text = "About",
                style = MaterialTheme.typography.headlineMedium,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ForestGreen.copy(alpha = 0.08f))
                    .border(1.dp, ForestGreen.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "🌿 FUN FACT",
                        style = MaterialTheme.typography.labelMedium,
                        color = ForestGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.funFact,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextDark
                    )
                }
            }
        }
    }
}

fun getAnimalEmoji(name: String): String = when {
    name.contains("Panther", true) || name.contains("Leopard", true) -> "🐆"
    name.contains("Elephant", true) -> "🐘"
    name.contains("Tiger", true) -> "🐯"
    name.contains("Squirrel", true) -> "🐿️"
    name.contains("Gaur", true) || name.contains("Bison", true) -> "🐂"
    name.contains("Hornbill", true) -> "🦜"
    name.contains("Eagle", true) -> "🦅"
    name.contains("Peafowl", true) || name.contains("Peacock", true) -> "🦚"
    name.contains("Sandalwood", true) -> "🌳"
    name.contains("Bamboo", true) -> "🎋"
    name.contains("Mango", true) -> "🥭"
    else -> "🌿"
}

fun getAnimalGradient(category: String): Brush = when (category) {
    "ANIMAL" -> Brush.verticalGradient(listOf(
        androidx.compose.ui.graphics.Color(0xFF2D5A3D),
        androidx.compose.ui.graphics.Color(0xFF1A3528)
    ))
    "BIRD" -> Brush.verticalGradient(listOf(
        androidx.compose.ui.graphics.Color(0xFF1E3A5F),
        androidx.compose.ui.graphics.Color(0xFF0F1F35)
    ))
    "FLORA" -> Brush.verticalGradient(listOf(
        androidx.compose.ui.graphics.Color(0xFF2D5A2D),
        androidx.compose.ui.graphics.Color(0xFF1A3C1A)
    ))
    else -> Brush.verticalGradient(listOf(ForestGreen, ForestGreenDark))
}