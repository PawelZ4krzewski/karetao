package com.example.karetao.data.repositoryinterface

import com.example.karetao.model.UserCard
import kotlinx.coroutines.flow.Flow

interface UserCardRepository {

    fun getUserCard(): Flow<List<UserCard>>

    fun getCardFromSameUser(username: String): Flow<List<UserCard>>

    suspend fun getUserCardById(id: Int): UserCard?

    suspend fun insertUserCard(userCard: UserCard)

    suspend fun  deleteUserCard(userCard: UserCard)

}