package com.example.karetao.test.data.repositoryinterface

import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow

interface FlashCardRepository {
    fun getFlashCards(): Flow<List<FlashCard>>

    fun getFlashCardsFromSameGroup(id: Int): Flow<List<FlashCard>>

    suspend fun getAmountOfFlashCard(id: Int): Int

    suspend fun getFlashCardById(id: Int): FlashCard?

    suspend fun insertFlashCards(flashCard: FlashCard)

    suspend fun insertMultipleFlashCards(flashCards: List<FlashCard>)

    suspend fun  deleteFlashCards(flashCard: FlashCard)
}