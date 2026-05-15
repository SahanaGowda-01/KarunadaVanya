package com.example.karunada_vanya.data.model

// WikiItem.kt
data class WikiItem(
    val id: String = "",
    val name: String = "",
    val category: String = "", // ANIMAL, BIRD, FLORA
    val region: String = "",
    val description: String = "",
    val funFact: String = "",
    val imageUrl: String = "",
    val conservationStatus: String = ""
)

// MovementAlert.kt
data class MovementAlert(
    val id: String = "",
    val speciesType: String = "",
    val location: String = "",
    val details: String = "",
    val reportedBy: String = "",
    val region: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isExpired: Boolean = false
) {
    fun isStillActive(): Boolean {
        val sixHoursInMillis = 6 * 60 * 60 * 1000L
        return (System.currentTimeMillis() - timestamp) < sixHoursInMillis
    }
}

// SafeTip.kt
data class SafeTip(
    val id: String = "",
    val title: String = "",
    val sector: String = "", // SAFETY, AGRICULTURE, HEALTH
    val description: String = ""
)

// AudioTrack.kt
data class AudioTrack(
    val id: String = "",
    val animalName: String = "",
    val region: String = "",
    val type: String = "FIELD RECORDING",
    val durationSeconds: Int = 0,
    val isPlaying: Boolean = false
)

// Region.kt
data class Region(
    val name: String,
    val displayName: String
)

val ALL_REGIONS = listOf(
    Region("ALL", "Regional Sync"),
    Region("NAGARAHOLE", "Nagarahole"),
    Region("BANDIPUR", "Bandipur"),
    Region("KABINI", "Kabini")
)