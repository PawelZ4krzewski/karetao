package com.example.karetao.data.repository

import com.example.karetao.data.DAO.FlashCardDao
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow

class FlashCardRepository constructor(private val dao: FlashCardDao) {

    fun getFlashCards(): Flow<List<FlashCard>> {
        return dao.getFlashCards()
    }

    suspend fun getFlashCardById(id: Int): FlashCard? {
        return dao.getFlashCardsById(id)
    }

    suspend fun insertFlashCards(flashCard: FlashCard){
        dao.insertFlashCards(flashCard)
    }

    suspend fun  deleeteFlashCards(flashCard: FlashCard){
        dao.deleteFlashCards(flashCard)
    }
}