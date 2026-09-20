package com.example.flashcards.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.addSet.AddSetViewModel
import com.example.flashcards.createCard.CreateCardViewModel
import com.example.flashcards.data.FlashcardRepository
import com.example.flashcards.list.FlashcardsSetsListViewModel
import com.example.flashcards.observeSet.ObserveSetViewModel

class FlashcardsSetsListViewFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(FlashcardsSetsListViewModel::class.java)){
            return FlashcardsSetsListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}

class CreatingCardViewFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(CreateCardViewModel::class.java)){
            return CreateCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}

class AddSetViewModelFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(AddSetViewModel::class.java)){
            return AddSetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}

class ObserveSetViewModelFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(ObserveSetViewModel::class.java)){
            return ObserveSetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}