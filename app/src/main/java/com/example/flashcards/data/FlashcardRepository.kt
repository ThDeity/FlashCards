package com.example.flashcards.data

import androidx.room.withTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FlashcardRepository(private val db: AppDatabase) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val allSets: StateFlow<List<FlashcardSet>> =
        db.flashcardSetDao().getAllSets()
            .stateIn(scope, SharingStarted.Eagerly, emptyList())

    suspend fun createSet(name: String, method: MethodOfPreparation): Long {
        val set = FlashcardSet(name = name, methodOfPreparation = method)
        return db.flashcardSetDao().insertSet(set)
    }

    suspend fun addCardToSet(setId: Long, front: String, back: String) {
        db.cardDao().insertCard(Flashcard(front = front, back = back, setId = setId))
    }

    suspend fun getCardsFromSet(setId: Long): SetWithCards =
        db.flashcardSetDao().getSetWithCards(setId)

    suspend fun deleteSetById(setId: Long) {
        db.flashcardSetDao().getSetById(setId)?.let { db.flashcardSetDao().deleteSet(it) }
    }

    suspend fun getSetById(setId: Long): FlashcardSet? =
        db.flashcardSetDao().getSetById(setId)

    suspend fun copySet(setId : Long) : Long?{
        val oldSet = db.flashcardSetDao().getSetById(setId) ?: return null
        val oldCards = db.flashcardSetDao().getSetWithCards(setId).flashcards

        return db.withTransaction {
            val newSetId = db.flashcardSetDao().insertSet(
                FlashcardSet(
                    name = "${oldSet.name} (copy)",
                    methodOfPreparation = oldSet.methodOfPreparation)
            )
            oldCards.forEach { card ->
                db.cardDao().insertCard(
                    Flashcard(
                        front = card.front,
                        back = card.back,
                        setId = newSetId
                    )
                )
            }
            newSetId
        }
    }
}