package com.example.karetao.data.db_tests

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.services.storage.internal.TestStorageUtil
import com.example.karetao.data.DAO.FlashCardDao
import com.example.karetao.data.DAO.UserCardDao
import com.example.karetao.data.database.AppDatabase
import com.example.karetao.data.database.AppDatabase_Impl
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FlashCardDataBaseTest {

    private lateinit var flashCardDao: FlashCardDao
    private lateinit var  db: AppDatabase


    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()

        flashCardDao = db.flashCardDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeFlashCardAndReadInList() = runBlocking{
        val flashCard: FlashCard = FlashCard(1,-1,"test","test")
        flashCardDao.insertFlashCards(flashCard)
        val gotFlashCard = flashCardDao.getFlashCardsById(1)
        assertEquals(flashCard, gotFlashCard)
    }

    @Test
    @Throws(Exception::class)
    fun writeFlashMultipleCardsAndReadInList() = runBlocking{
        val flashCard1: FlashCard = FlashCard(1,-1,"","test1")
        val flashCard2: FlashCard = FlashCard(2,-1,"test2","test2")
        flashCardDao.insertMultipleFlashCards(listOf(flashCard1,flashCard2))
        val gotFlashCards = flashCardDao.getFlashCards().first()
        Log.d("DB-test",gotFlashCards.toString())
        assertEquals(gotFlashCards, listOf(flashCard1,flashCard2))
    }
}
