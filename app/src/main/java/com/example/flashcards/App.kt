package com.example.flashcards

import android.app.Application
import androidx.room.Room
import com.example.flashcards.data.AppDatabase
import com.example.flashcards.data.FlashcardRepository

class App: Application() {
    lateinit var database : AppDatabase
    private set
    lateinit var repository : FlashcardRepository

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "flashcards.db"
        ).build()

        repository = FlashcardRepository(database)
    }
}