package com.example.karunada_vanya.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karunada_vanya.data.model.SafeTip
import com.example.karunada_vanya.data.repository.SafeGuideRepository
import com.example.karunada_vanya.ui.theme.*

@Composable
fun SafeGuideScreen(modifier: Modifier = Modifier) {
    val tips = SafeGuideRepository.getAllTips()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamWhite)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
            Text(
                text = "SURVIVAL & CO-EXISTENCE",
                style = MaterialTheme.typography.labelLarge,
                color = WarmOrange,
                letterSpacing = 3.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Safety Guide",
                style = MaterialTheme.typography.displayMedium.copy(fontStyle = FontStyle.Italic),
                color = TextDark
            )
            Divider(
                color = DividerColor,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tips) { tip ->
                SafeTipCard(tip = tip)
            }
        }
    }
}

@Composable
fun SafeTipCard(tip: SafeTip) {
    val sectorColor = when (tip.sector) {
        "SAFETY" -> TextMuted
        "AGRICULTURE" -> androidx.compose.ui.graphics.Color(0xFF22C55E)
        "HEALTH" -> AlertRed
        else -> TextMuted
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardWhite)
            .border(1.dp, DividerColor, RoundedCornerShape(20.dp))
            .padding(24.dp)
    ) {
        Column {
            Text(
                text = "SECTOR: ${tip.sector}",
                style = MaterialTheme.typography.labelSmall,
                color = sectorColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = tip.title,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp),
                color = TextDark
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "\"${tip.description}\"",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                color = TextMuted
            )
        }
    }
}