package com.example.karunada_vanya.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karunada_vanya.navigation.Screen
import com.example.karunada_vanya.ui.theme.*

data class NavItem(
    val screen: Screen,
    val label: String
)

val navItems = listOf(
    NavItem(Screen.Home, "OVERVIEW"),
    NavItem(Screen.Wiki, "WILDLIFE WIKI"),
    NavItem(Screen.Movement, "LAND MOVEMENT"),
    NavItem(Screen.SafeGuide, "SAFE GUIDE"),
    NavItem(Screen.ForestAudio, "FOREST AUDIO")
)

@Composable
fun DrawerContent(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(260.dp)
            .background(ForestGreen)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(WarmOrange.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("🌿", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Karunada",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    color = CardWhite
                )
                Text(
                    text = "Vanya",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    ),
                    color = CardWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = WarmOrange, thickness = 2.dp, modifier = Modifier.width(40.dp))
        Spacer(modifier = Modifier.height(40.dp))

        // Nav Items
        navItems.forEach { item ->
            val isSelected = currentScreen == item.screen
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(item.screen) }
                    .padding(vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            if (isSelected) WarmOrange else CardWhite.copy(alpha = 0.3f),
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) CardWhite else CardWhite.copy(alpha = 0.5f),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                if (isSelected) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(20.dp)
                            .background(WarmOrange)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Heritage Pride Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ForestGreenDark, MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "HERITAGE PRIDE",
                    style = MaterialTheme.typography.labelSmall,
                    color = WarmOrange
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "\"Protecting the Western Ghats for the next generation of Karnataka.\"",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                    color = CardWhite.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}