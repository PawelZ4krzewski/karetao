package com.example.karetao.data.repository

import com.example.karetao.data.DAO.CardGroupDao
import com.example.karetao.model.CardGroup
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardGroupRepository @Inject constructor(private val dao: CardGroupDao) {

    fun getCardGroups(): Flow<List<CardGroup>> {
        return dao.getCardGroups()
    }

    suspend fun getCardGroupById(id: Int): CardGroup? {
        return dao.getCardGroupById(id)
    }

    suspend fun insertCardGroup(cardGroup: CardGroup){
        dao.insertCardGroup(cardGroup)
    }

    suspend fun  deleteCardGroup(cardGroup: CardGroup){
        dao.deleteCardGroup(cardGroup)
    }
}