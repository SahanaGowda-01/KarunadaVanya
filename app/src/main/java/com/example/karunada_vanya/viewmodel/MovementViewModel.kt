package com.example.karunada_vanya.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karunada_vanya.data.model.MovementAlert
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class MovementUiState(
    val alerts: List<MovementAlert> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val submitSuccess: Boolean = false,
    val submitError: String? = null
)

class MovementViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val alertsCollection = db.collection("movement_alerts")

    private val _uiState = MutableStateFlow(MovementUiState())
    val uiState: StateFlow<MovementUiState> = _uiState.asStateFlow()

    init {
        listenToAlerts()
    }

    private fun listenToAlerts() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        alertsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Failed to load alerts"
                    )
                    return@addSnapshotListener
                }

                val sixHoursAgo = System.currentTimeMillis() - (6 * 60 * 60 * 1000L)
                val alerts = snapshot?.documents?.mapNotNull { doc ->
                    val alert = doc.toObject(MovementAlert::class.java)?.copy(id = doc.id)
                    // Only return alerts from last 6 hours
                    if (alert != null && alert.timestamp > sixHoursAgo) alert else null
                } ?: emptyList()

                _uiState.value = _uiState.value.copy(
                    alerts = alerts,
                    isLoading = false,
                    errorMessage = null
                )
            }
    }

    fun submitAlert(
        speciesType: String,
        location: String,
        details: String,
        region: String = "Mysore"
    ) {
        if (speciesType.isBlank() || location.isBlank()) {
            _uiState.value = _uiState.value.copy(submitError = "Please fill in all required fields")
            return
        }

        viewModelScope.launch {
            try {
                val alert = MovementAlert(
                    speciesType = speciesType,
                    location = location,
                    details = details,
                    reportedBy = "Sahana", // In real app, use logged in user
                    region = region,
                    timestamp = System.currentTimeMillis()
                )
                alertsCollection.add(alert).await()
                _uiState.value = _uiState.value.copy(submitSuccess = true, submitError = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(submitError = "Failed to submit alert: ${e.message}")
            }
        }
    }

    fun clearSubmitState() {
        _uiState.value = _uiState.value.copy(submitSuccess = false, submitError = null)
    }
}