package com.example.karunada_vanya.data.repository

import com.example.karunada_vanya.data.model.AudioTrack
import com.example.karunada_vanya.data.model.SafeTip

object SafeGuideRepository {
    fun getAllTips(): List<SafeTip> = listOf(
        SafeTip(
            id = "1",
            title = "Elephant in your field?",
            sector = "SAFETY",
            description = "Do not approach. Maintain a safe distance of at least 100 meters. Use bright lights or loud noises from a distance to encourage them to move back to the forest."
        ),
        SafeTip(
            id = "2",
            title = "Night Travel",
            sector = "SAFETY",
            description = "Avoid walking alone on forest border roads after sunset. If you must travel, use a vehicle and keep windows rolled up."
        ),
        SafeTip(
            id = "3",
            title = "Crop Protection",
            sector = "AGRICULTURE",
            description = "Use bee-fencing or chili fences. These are natural deterrents that keep elephants away without causing them harm."
        ),
        SafeTip(
            id = "4",
            title = "Leopard Sighting",
            sector = "SAFETY",
            description = "Do not run. Stand tall, make yourself look large, and back away slowly. Never turn your back to a leopard. Alert your village immediately."
        ),
        SafeTip(
            id = "5",
            title = "Encountering an Elephant",
            sector = "SAFETY",
            description = "Avoid flashlights when encountering an elephant. Speak in low, calm tones to show non-aggression. Never get between a mother and her calf."
        ),
        SafeTip(
            id = "6",
            title = "Snake Safety",
            sector = "SAFETY",
            description = "Wear boots and use a torch at night. If bitten, keep the bitten limb below heart level, keep calm, and reach a hospital immediately. Do not tie a tourniquet."
        ),
        SafeTip(
            id = "7",
            title = "Water Source Protection",
            sector = "AGRICULTURE",
            description = "Fence your wells and water tanks. Wildlife often comes to farms for water, especially in dry season. Provide an alternative water source at the forest edge."
        ),
        SafeTip(
            id = "8",
            title = "Fire Safety",
            sector = "SAFETY",
            description = "Never light fires near forest borders during dry months. A single spark can trigger a forest fire that destroys habitat for all wildlife."
        )
    )
}

object AudioRepository {
    fun getAllTracks(): List<AudioTrack> = listOf(
        AudioTrack(
            id = "1",
            animalName = "Black Panther",
            region = "KABINI",
            type = "FIELD RECORDING",
            durationSeconds = 0
        ),
        AudioTrack(
            id = "2",
            animalName = "Asian Elephant",
            region = "BANDIPUR",
            type = "FIELD RECORDING",
            durationSeconds = 0
        ),
        AudioTrack(
            id = "3",
            animalName = "Bengal Tiger",
            region = "NAGARAHOLE",
            type = "FIELD RECORDING",
            durationSeconds = 0
        ),
        AudioTrack(
            id = "4",
            animalName = "Malabar Hornbill",
            region = "NAGARAHOLE",
            type = "FIELD RECORDING",
            durationSeconds = 0
        ),
        AudioTrack(
            id = "5",
            animalName = "Indian Peafowl",
            region = "BANDIPUR",
            type = "FIELD RECORDING",
            durationSeconds = 0
        ),
        AudioTrack(
            id = "6",
            animalName = "Crested Serpent Eagle",
            region = "KABINI",
            type = "FIELD RECORDING",
            durationSeconds = 0
        )
    )

    fun getTracksByRegion(region: String): List<AudioTrack> {
        if (region == "ALL") return getAllTracks()
        return getAllTracks().filter { it.region == region }
    }
}