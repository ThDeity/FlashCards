package com.example.flashcards.observeSet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.FlashcardRepository
import com.example.flashcards.data.SetWithCards
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ObserveSetViewModel(private val repository: FlashcardRepository) : ViewModel() {
    private val _setWithCards = MutableStateFlow<SetWithCards?>(null)
    val setWithCards: StateFlow<SetWithCards?> = _setWithCards.asStateFlow()

    fun load(setId: Long){
        viewModelScope.launch {
            _setWithCards.value = repository.getCardsFromSet(setId)
        }
    }

    fun deleteSet(id: Long){
        viewModelScope.launch {
            repository.deleteSetById(id)
        }
    }
}