package com.example.karetao.data.repository

import com.example.karetao.data.repositoryinterface.FlashCardRepository
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlashCardRepository: FlashCardRepository {

    private val flashCards = mutableListOf<FlashCard>()

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return flow { emit(flashCards) }
    }

    override fun getFlashCardsFromSameGroup(id: Int): Flow<List<FlashCard>> {
        return flow{ emit(flashCards.filter {it.groupId == id})}

    }

    override suspend fun getAmountOfFlashCard(id: Int): Int {
        return flashCards.size
    }

    override suspend fun getFlashCardById(id: Int): FlashCard? {
        return flashCards.find {it.cardId == id}
    }

    override suspend fun insertFlashCards(flashCard: FlashCard) {
        flashCards.add(flashCard)
    }

    override suspend fun insertMultipleFlashCards(flashCards: List<FlashCard>) {
        this.flashCards.addAll(flashCards)
    }

    override suspend fun deleteFlashCards(flashCard: FlashCard) {
        flashCards.remove(flashCard)
    }
}