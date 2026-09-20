package com.example.flashcards.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = FlashcardSet::class,
            parentColumns = ["id"],
            childColumns = ["setId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("setId")])
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val front: String,
    val back: String,
    val setId: Long
)
