package com.example.karetao.data.DAO

import androidx.room.*
import com.example.karetao.model.CardGroup
import com.example.karetao.model.FlashCard
import com.example.karetao.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashCard")
    fun getFlashCards(): Flow<List<FlashCard>>

    @Query("SELECT * From flashCard WHERE groupId = :id")
    fun getFlashCardsFromSameGroup(id: Int): Flow<List<FlashCard>>

    @Query("SELECT COUNT(*) From flashCard WHERE groupId = :id")
    fun getAmountOfFlashCard(id: Int): Int

    @Query("SELECT * From flashCard WHERE cardId = :id")
    suspend fun getFlashCardsById(id: Int): FlashCard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCards(flashCard: FlashCard)

    @Delete
    suspend fun deleteFlashCards(flashCard: FlashCard)
}