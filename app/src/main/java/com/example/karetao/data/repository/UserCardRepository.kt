package com.example.karetao.data.repository

import com.example.karetao.data.DAO.UserCardDao
import com.example.karetao.model.FlashCard
import com.example.karetao.model.UserCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserCardRepository @Inject constructor(private val dao: UserCardDao) {

    fun getUserCard(): Flow<List<UserCard>> {
        return dao.getUserCard()
    }

    fun getCardFromSameUser(username: String): Flow<List<UserCard>> {
        return dao.getCardFromSameUser(username)
    }

    suspend fun getUserCardById(id: Int): UserCard? {
        return dao.getUserCardById(id)
    }

    suspend fun insertUserCard(userCard: UserCard){
        dao.insertUserCard(userCard)
    }

    suspend fun  deleteUserCard(userCard: UserCard){
        dao.deleteUserCard(userCard)
    }

}