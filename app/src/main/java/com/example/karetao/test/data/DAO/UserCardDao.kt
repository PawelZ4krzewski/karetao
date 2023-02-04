package com.example.karetao.test.data.DAO

import androidx.room.*
import com.example.karetao.model.UserCard
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCardDao {
    @Query("SELECT * FROM usercard")
    fun getUserCard(): Flow<List<UserCard>>

    @Query("SELECT * From usercard WHERE username = :username")
    fun getCardFromSameUser(username: String): Flow<List<UserCard>>

    @Query("SELECT * From usercard WHERE cardId = :id")
    suspend fun getUserCardById(id: Int): UserCard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserCard(usercard: UserCard)

    @Delete
    suspend fun deleteUserCard(usercard: UserCard)

}