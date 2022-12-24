package com.example.karetao.data.repository

import com.example.karetao.data.DAO.CardGroupDao
import com.example.karetao.data.repositoryinterface.CardGroupRepository
import com.example.karetao.model.CardGroup
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardGroupRepositoryImpl @Inject constructor(private val dao: CardGroupDao): CardGroupRepository {

    override fun getCardGroups(): Flow<List<CardGroup>> {
        return dao.getCardGroups()
    }

    override suspend fun getCardGroupById(id: Int): CardGroup? {
        return dao.getCardGroupById(id)
    }

    override suspend fun insertCardGroup(cardGroup: CardGroup){
        dao.insertCardGroup(cardGroup)
    }

    override suspend fun  deleteCardGroup(cardGroup: CardGroup){
        dao.deleteCardGroup(cardGroup)
    }
}