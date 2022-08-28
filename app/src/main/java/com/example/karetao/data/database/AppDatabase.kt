package com.example.karetao.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.karetao.data.DAO.FlashCardDao
import com.example.karetao.model.*

@Database(
    entities = [CardGroup::class, FlashCard::class, User::class, UserCard::class, UserGroup::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

//    abstract val userDao: UserDao
    abstract val flashCardDao: FlashCardDao

    companion object{
        const val DATABASE_NAME = "flashCard_database"
    }
}