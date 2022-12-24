package com.example.karetao.data.repositoryinterface

import com.example.karetao.model.CardGroup
import kotlinx.coroutines.flow.Flow

interface CardGroupRepository {
    fun getCardGroups(): Flow<List<CardGroup>>

    suspend fun getCardGroupById(id: Int): CardGroup?

    suspend fun insertCardGroup(cardGroup: CardGroup)

    suspend fun  deleteCardGroup(cardGroup: CardGroup)
}