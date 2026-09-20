package com.example.flashcards.createCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.FlashcardRepository
import com.example.flashcards.data.FlashcardSet
import com.example.flashcards.data.SetWithCards
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateCardViewModel(private val repository: FlashcardRepository) : ViewModel() {
    private val _deck = MutableStateFlow<FlashcardSet?>(null)
    val deck: StateFlow<FlashcardSet?> = _deck.asStateFlow()
    fun addCard(setId: Long, front: String, back: String){
        viewModelScope.launch {
            repository.addCardToSet(setId, front, back)
        }
    }

    fun load(setsId: Long){
        viewModelScope.launch {
            _deck.value = repository.getSetById(setsId)
        }
    }
}