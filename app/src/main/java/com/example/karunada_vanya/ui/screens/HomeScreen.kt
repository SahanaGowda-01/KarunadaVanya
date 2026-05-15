package com.example.karunada_vanya.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karunada_vanya.data.model.MovementAlert
import com.example.karunada_vanya.ui.theme.*

@Composable
fun HomeScreen(
    activeAlerts: List<MovementAlert>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero Banner
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(320.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ForestGreen,
                                ForestGreenDark
                            )
                        )
                    )
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "THE PRIDE PROJECT",
                        style = MaterialTheme.typography.labelLarge,
                        color = WarmOrange,
                        letterSpacing = 3.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Karnataka",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontStyle = FontStyle.Normal,
                            fontSize = 42.sp
                        ),
                        color = CardWhite
                    )
                    Text(
                        text = "Wildlands",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontStyle = FontStyle.Italic,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Light
                        ),
                        color = CardWhite.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "NATURE CONSERVATION",
                            style = MaterialTheme.typography.labelSmall,
                            color = CardWhite.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(WarmOrange, androidx.compose.foundation.shape.CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "REAL-TIME SAFETY",
                            style = MaterialTheme.typography.labelSmall,
                            color = CardWhite.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Experience the harmony of the Western Ghats. Educating children through stories and protecting farmers with intelligent alerts.",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                        color = CardWhite.copy(alpha = 0.8f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Right Column
            Column(
                modifier = Modifier.width(280.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Active Alerts Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(AlertRedLight)
                        .border(1.dp, AlertRed.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Alerts",
                                tint = AlertRed,
                                modifier = Modifier.size(28.dp)
                            )
                            if (activeAlerts.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .background(AlertRed, RoundedCornerShape(20.dp))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${activeAlerts.size} ACTIVE",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CardWhite,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "ACTIVE ALERTS",
                            style = MaterialTheme.typography.titleMedium,
                            color = AlertRed,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (activeAlerts.isNotEmpty()) {
                            Text(
                                text = activeAlerts.first().let {
                                    "${it.speciesType} movement detected near ${it.location}."
                                },
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 13.sp
                                ),
                                color = AlertRed.copy(alpha = 0.8f)
                            )
                        } else {
                            Text(
                                text = "All sectors clear. No movements reported.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted
                            )
                        }
                    }
                }

                // Daily Safety Tip
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(WarmOrange)
                        .padding(20.dp)
                ) {
                    Column {
                        Text(
                            text = "DAILY SAFETY TIP",
                            style = MaterialTheme.typography.labelSmall,
                            color = CardWhite.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "\"Avoid flashlights when encountering an Elephant. Speak in low, calm tones to show non-aggression.\"",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                            color = CardWhite
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "© 2026 KARUNADA VANYA INITIATIVE",
                style = MaterialTheme.typography.labelSmall,
                color = TextLight
            )
        }
    }
}