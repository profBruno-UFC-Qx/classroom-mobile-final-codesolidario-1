package com.example.givchurch.viewmodel.bible

import com.example.givchurch.domain.model.Verse

sealed interface BibleUiState {
    object Loading : BibleUiState
    data class Success(val verse: Verse) : BibleUiState
    data class Error(val fallbackVerse: String) : BibleUiState
}
