package com.example.flashcards.addSet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.FlashcardRepository
import com.example.flashcards.data.MethodOfPreparation
import kotlinx.coroutines.launch

class AddSetViewModel(private val repository: FlashcardRepository) : ViewModel() {
    fun addSet(name: String, method: MethodOfPreparation){
        viewModelScope.launch {
            repository.createSet(name, method)
        }
    }
}