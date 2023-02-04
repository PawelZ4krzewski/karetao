package com.example.karetao.test.data.repository

import com.example.karetao.test.data.DAO.FlashCardDao
import com.example.karetao.test.data.repositoryinterface.FlashCardRepository
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlashCardRepositoryImpl @Inject constructor(private val dao: FlashCardDao):
    FlashCardRepository {

    override fun getFlashCards(): Flow<List<FlashCard>> {
        return dao.getFlashCards()
    }

    override fun getFlashCardsFromSameGroup(id: Int): Flow<List<FlashCard>> {
        return dao.getFlashCardsFromSameGroup(id)
    }

    override suspend fun getAmountOfFlashCard(id: Int): Int {
        return dao.getAmountOfFlashCard(id)
    }

    override suspend fun getFlashCardById(id: Int): FlashCard? {
        return dao.getFlashCardsById(id)
    }

    override suspend fun insertFlashCards(flashCard: FlashCard){
        dao.insertFlashCards(flashCard)
    }

    override suspend fun insertMultipleFlashCards(flashCards: List<FlashCard>){
        dao.insertMultipleFlashCards(flashCards)
    }

    override suspend fun  deleteFlashCards(flashCard: FlashCard){
        dao.deleteFlashCards(flashCard)
    }
}