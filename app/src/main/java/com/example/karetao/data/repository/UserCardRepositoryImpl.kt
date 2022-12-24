package com.example.karetao.data.repository

import com.example.karetao.data.DAO.UserCardDao
import com.example.karetao.data.repositoryinterface.UserCardRepository
import com.example.karetao.model.UserCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserCardRepositoryImpl @Inject constructor(private val dao: UserCardDao):
    UserCardRepository {

    override fun getUserCard(): Flow<List<UserCard>> {
        return dao.getUserCard()
    }

    override fun getCardFromSameUser(username: String): Flow<List<UserCard>> {
        return dao.getCardFromSameUser(username)
    }

    override suspend fun getUserCardById(id: Int): UserCard? {
        return dao.getUserCardById(id)
    }

    override suspend fun insertUserCard(userCard: UserCard){
        dao.insertUserCard(userCard)
    }

    override suspend fun  deleteUserCard(userCard: UserCard){
        dao.deleteUserCard(userCard)
    }

}