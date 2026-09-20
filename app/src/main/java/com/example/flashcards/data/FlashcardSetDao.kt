package com.example.flashcards.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

data class SetWithCards(
    @Embedded val set: FlashcardSet,
    @Relation(
        parentColumn = "id",
        entityColumn = "setId"
    )
    val flashcards: List<Flashcard>
)

@Dao
interface FlashcardSetDao {
    @Insert
    suspend fun insertSet(flashcardSet: FlashcardSet): Long

    @Update
    suspend fun updateSet(flashcardSet: FlashcardSet)

    @Delete
    suspend fun deleteSet(flashcardSet: FlashcardSet)

    @Query("SELECT * FROM sets")
    suspend fun observeAllSets(): List<FlashcardSet>

    @Query("SELECT * FROM sets")
    fun getAllSets(): Flow<List<FlashcardSet>>

    @Query("SELECT * FROM sets WHERE id = :setId")
    suspend fun getSetById(setId: Long): FlashcardSet?

    @Transaction
    @Query("SELECT * FROM sets WHERE id = :setId")
    suspend fun getSetWithCards(setId: Long): SetWithCards
}

@Dao
interface CardDao{
    @Insert
    suspend fun insertCard(flashcard: Flashcard): Long

    @Update
    suspend fun updateCard(flashcard: Flashcard)

    @Delete
    suspend fun deleteCard(flashcard: Flashcard)

    @Query("SELECT * FROM cards WHERE setId = :setId ORDER BY id DESC")
    fun getAllCardsFromSet(setId: Long): Flow<List<Flashcard>>

    @Query("SELECT * FROM cards WHERE id = :cardId")
    suspend fun getCardById(cardId: Long): Flashcard?
}