package com.example.karetao.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.karetao.test.data.DAO.CardGroupDao
import com.example.karetao.test.data.DAO.FlashCardDao
import com.example.karetao.test.data.DAO.UserCardDao
import com.example.karetao.model.*

@Database(
    entities = [CardGroup::class, FlashCard::class, User::class, UserCard::class, UserGroup::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

//    abstract val userDao: UserDao
    abstract val flashCardDao: FlashCardDao
    abstract val cardGroupDao: CardGroupDao
    abstract val userCardDao: UserCardDao

    companion object{
        const val DATABASE_NAME = "flashCard_database"
    }
}