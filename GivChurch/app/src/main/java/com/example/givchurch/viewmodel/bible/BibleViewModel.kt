package com.example.givchurch.viewmodel.bible

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.givchurch.domain.repository.BibleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BibleViewModel(
    private val bibleRepository: BibleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BibleUiState>(BibleUiState.Loading)
    val uiState: StateFlow<BibleUiState> = _uiState.asStateFlow()

    init {
        loadVerse()
    }

    fun loadVerse() {
        viewModelScope.launch {
            _uiState.value = BibleUiState.Loading
            bibleRepository.getVerseOfTheDay()
                .onSuccess { verse ->
                    _uiState.value = BibleUiState.Success(verse)
                }
                .onFailure {
                    _uiState.value = BibleUiState.Error(
                        "\"O Senhor é o meu pastor, nada me faltará.\" Salmos 23:1"
                    )
                }
        }
    }
}
