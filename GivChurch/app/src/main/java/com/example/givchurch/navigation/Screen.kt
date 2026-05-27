package com.example.givchurch.navigation

sealed class Screen {
    data object Login: Screen()
    data object Register: Screen()
}