package com.example.flashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class FlashcardSet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis(),
    val methodOfPreparation: MethodOfPreparation
)

enum class MethodOfPreparation{
    Test, Input, ByYourSelf
}