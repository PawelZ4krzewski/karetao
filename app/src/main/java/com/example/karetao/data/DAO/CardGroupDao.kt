package com.example.karetao.data.DAO

import androidx.room.*
import com.example.karetao.model.CardGroup
import kotlinx.coroutines.flow.Flow


@Dao
interface CardGroupDao {
    @Query("SELECT * FROM CardGroup")
    fun getCardGroups(): Flow<List<CardGroup>>

    @Query("SELECT * From CardGroup WHERE groupId = :id")
    suspend fun getCardGroupById(id: Int): CardGroup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardGroup(cardGroup: CardGroup)

    @Delete
    suspend fun deleteCardGroup(cardGroup: CardGroup)
}