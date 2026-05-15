package com.example.karunada_vanya.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.karunada_vanya.ui.theme.*

@Composable
fun TopBar(
    currentRegion: String,
    onRegionSelected: (String) -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val regions = listOf("Regional Sync", "Nagarahole", "Bandipur", "Kabini")
    val regionKeys = listOf("ALL", "NAGARAHOLE", "BANDIPUR", "KABINI")
    val isTablet = LocalConfiguration.current.screenWidthDp >= 600

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CreamWhite)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Show hamburger only on phones
        if (!isTablet) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open menu",
                    tint = TextDark
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
        }

        // Region tabs — scrollable so they fit on small screens
        Row(
            modifier = Modifier
                .weight(1f)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            regions.forEachIndexed { index, region ->
                val key = regionKeys[index]
                val isSelected = currentRegion == key
                Text(
                    text = region.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isSelected) WarmOrange else TextMuted,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .clickable { onRegionSelected(key) }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        // User avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(ForestGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "S",
                style = MaterialTheme.typography.titleMedium,
                color = CardWhite,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "SAHANA",
                style = MaterialTheme.typography.labelSmall,
                color = TextDark,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "LOGOUT",
                style = MaterialTheme.typography.labelSmall,
                color = WarmOrange
            )
        }
    }
}

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CreamWhite)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = androidx.compose.ui.graphics.Color(0xFF22C55E),
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "SYSTEM ONLINE",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "DISTRICT: MYSORE",
                style = MaterialTheme.typography.labelSmall,
                color = TextLight
            )
        }
    }
}