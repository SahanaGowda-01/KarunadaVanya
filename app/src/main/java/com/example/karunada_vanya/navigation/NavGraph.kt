package com.example.karunada_vanya.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Wiki : Screen("wiki")
    object Movement : Screen("movement")
    object SafeGuide : Screen("safe_guide")
    object ForestAudio : Screen("forest_audio")
}