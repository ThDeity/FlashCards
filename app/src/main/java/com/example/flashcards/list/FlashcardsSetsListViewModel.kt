package com.example.flashcards.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.FlashcardRepository
import kotlinx.coroutines.launch

class FlashcardsSetsListViewModel(private val repository: FlashcardRepository) : ViewModel() {
    val allSets = repository.allSets

    fun deleteSet(id: Long){
        viewModelScope.launch {
            repository.deleteSetById(id)
        }
    }

    fun copySet(id: Long){
        viewModelScope.launch {
            repository.copySet(id)
        }
    }
}