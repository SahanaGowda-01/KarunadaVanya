@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.karunada_vanya.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karunada_vanya.data.model.MovementAlert
import com.example.karunada_vanya.ui.theme.*
import com.example.karunada_vanya.viewmodel.MovementViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MovementScreen(
    viewModel: MovementViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.submitSuccess) {
        if (uiState.submitSuccess) {
            showDialog = false
            viewModel.clearSubmitState()
        }
    }

    if (showDialog) {
        ReportMovementDialog(
            onDismiss = {
                showDialog = false
                viewModel.clearSubmitState()
            },
            onSubmit = { species, location, details ->
                viewModel.submitAlert(species, location, details)
            },
            submitError = uiState.submitError
        )
    }

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(CreamWhite)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Left: Alerts list
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Movement",
                style = MaterialTheme.typography.displayMedium,
                color = TextDark
            )
            Text(
                text = "Alerts",
                style = MaterialTheme.typography.displayMedium.copy(fontStyle = FontStyle.Italic),
                color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "6-Hour Expiry Policy enforced for accuracy.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ForestGreen)
                }
            } else if (uiState.alerts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(CardWhite)
                        .border(1.dp, DividerColor, RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📍", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "All sectors clear. No movements reported.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                            color = TextMuted
                        )
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.alerts) { alert ->
                        AlertCard(alert = alert)
                    }
                }
            }
        }

        // Right: Action panel
        Column(
            modifier = Modifier.width(220.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ForestGreen)
                    .clickable { showDialog = true }
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Report",
                        tint = CardWhite,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "REPORT MOVEMENT",
                        style = MaterialTheme.typography.labelMedium,
                        color = CardWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardWhite)
                    .border(1.dp, DividerColor, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "FEED STATUS",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FeedStatusRow("REAL-TIME SYNC PATH")
                    Spacer(modifier = Modifier.height(10.dp))
                    FeedStatusRow("GEOFENCE VERIFIED")
                    Spacer(modifier = Modifier.height(10.dp))
                    FeedStatusRow("6H EXPIRY ACTIVE")
                }
            }

            uiState.errorMessage?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = AlertRed
                )
            }
        }
    }
}

@Composable
fun FeedStatusRow(label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(TextLight, androidx.compose.foundation.shape.CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted
        )
    }
}

@Composable
fun AlertCard(alert: MovementAlert) {
    val timeAgo = getTimeAgo(alert.timestamp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .border(1.dp, DividerColor, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(AlertRed, androidx.compose.foundation.shape.CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = alert.speciesType.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = AlertRed,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextLight
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📍 ${alert.location}",
                style = MaterialTheme.typography.titleMedium,
                color = TextDark
            )
            if (alert.details.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = alert.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Reported by ${alert.reportedBy}",
                style = MaterialTheme.typography.labelSmall,
                color = TextLight
            )
        }
    }
}

@Composable
fun ReportMovementDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String) -> Unit,
    submitError: String?
) {
    var selectedSpecies by remember { mutableStateOf("Elephant") }
    var location by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val speciesOptions = listOf("Elephant", "Leopard", "Tiger", "Black Panther", "Wild Boar", "Bison", "Other")

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(CreamWhite)
                .padding(28.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alert the Village",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontStyle = FontStyle.Italic,
                            fontSize = 26.sp
                        ),
                        color = TextDark
                    )
                    IconButton(onClick = onDismiss) {
                        Text("✕", color = TextMuted, fontSize = 18.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Species Dropdown
                Text(
                    text = "SPECIES TYPE",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedSpecies.uppercase(),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = DividerColor,
                            focusedBorderColor = ForestGreen,
                            unfocusedContainerColor = CardWhite,
                            focusedContainerColor = CardWhite
                        )
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        speciesOptions.forEach { species ->
                            DropdownMenuItem(
                                text = { Text(species.uppercase()) },
                                onClick = {
                                    selectedSpecies = species
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Location
                Text(
                    text = "PRECISE LOCATION",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("e.g. Village Border", color = TextLight) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = DividerColor,
                        focusedBorderColor = ForestGreen,
                        unfocusedContainerColor = CardWhite,
                        focusedContainerColor = CardWhite
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Details
                Text(
                    text = "DETAILS",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    placeholder = {
                        Text(
                            "Direction of movement...",
                            color = TextLight,
                            fontStyle = FontStyle.Italic
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = DividerColor,
                        focusedBorderColor = ForestGreen,
                        unfocusedContainerColor = CardWhite,
                        focusedContainerColor = CardWhite
                    )
                )

                submitError?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, style = MaterialTheme.typography.bodySmall, color = AlertRed)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onSubmit(selectedSpecies, location, details) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
                ) {
                    Text(
                        text = "PUSH ALERT",
                        style = MaterialTheme.typography.labelLarge,
                        color = CardWhite
                    )
                }
            }
        }
    }
}

fun getTimeAgo(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp
    val minutes = diff / 60000
    val hours = minutes / 60
    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 6 -> "${hours}h ago"
        else -> "Expired"
    }
}