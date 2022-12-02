package com.example.karetao.data.repository

import com.example.karetao.data.DAO.FlashCardDao
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow

class FlashCardRepository constructor(private val dao: FlashCardDao) {

    fun getFlashCards(): Flow<List<FlashCard>> {
        return dao.getFlashCards()
    }

    fun getFlashCardsFromSameGroup(id: Int): Flow<List<FlashCard>> {
        return dao.getFlashCardsFromSameGroup(id)
    }

    fun getAmountOfFlashCard(id: Int): Int {
        return dao.getAmountOfFlashCard(id)
    }

    suspend fun getFlashCardById(id: Int): FlashCard? {
        return dao.getFlashCardsById(id)
    }

    suspend fun insertFlashCards(flashCard: FlashCard){
        dao.insertFlashCards(flashCard)
    }

    suspend fun  deleteFlashCards(flashCard: FlashCard){
        dao.deleteFlashCards(flashCard)
    }
}