package com.example.karetao.data.use_case.flashCard

import androidx.test.espresso.core.internal.deps.guava.cache.CacheLoader
import com.example.karetao.data.repository.FakeFlashCardRepository
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidFlashCardException
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.rules.ExpectedException

class AddMultipleCardsUseCaseTest{
    private lateinit var addMultipleCardsUseCase: AddMultipleCardsUseCase
    private lateinit var fakeRepository: FakeFlashCardRepository


    @Before
    fun setUp(){
        fakeRepository = FakeFlashCardRepository()
        addMultipleCardsUseCase = AddMultipleCardsUseCase(fakeRepository)

    }

    @Test
    fun testAddingMultipleFlashCards() = runBlocking{

        val flashCardsToInsert = mutableListOf<FlashCard>()
        ('a'..'z').forEachIndexed { index, c ->
            flashCardsToInsert.add(
                FlashCard(
                    cardId = index,
                    groupId = 1,
                    question = c.toString(),
                    answer= c.toString()
                )
            )
        }

        addMultipleCardsUseCase.invoke(flashCardsToInsert)
    }

    @Test
    fun testAddingMultipleFlashCardsWithoutQuestion() = runBlocking{

        val flashCardsToInsert = mutableListOf<FlashCard>()
        ('a'..'z').forEachIndexed { index, c ->
            flashCardsToInsert.add(
                FlashCard(
                    cardId = index,
                    groupId = 1,
                    question = "",
                    answer= c.toString()
                )
            )
        }

        val exception = try {
            addMultipleCardsUseCase.invoke(flashCardsToInsert)
            null
        } catch (exception: InvalidFlashCardException){
            exception
        }

        assertEquals(exception?.message,"Any question of the flashcard can't be empty.")

    }

    @Test
    fun testAddingMultipleFlashCardsWithoutOneQuestion() = runBlocking{

        val flashCardsToInsert = mutableListOf<FlashCard>()
        ('a'..'z').forEachIndexed { index, c ->
            flashCardsToInsert.add(
                FlashCard(
                    cardId = index,
                    groupId = 1,
                    question = c.toString(),
                    answer= c.toString()
                )
            )
        }

        flashCardsToInsert[flashCardsToInsert.size/2] = flashCardsToInsert[flashCardsToInsert.size/2].copy(
            question = ""
        )

        val exception = try {
            addMultipleCardsUseCase.invoke(flashCardsToInsert)
            null
        } catch (exception: InvalidFlashCardException){
            exception
        }

        assertEquals(exception?.message,"Any question of the flashcard can't be empty.")

    }

    @Test
    fun testAddingMultipleFlashCardsWithoutAnswer() = runBlocking{

        val flashCardsToInsert = mutableListOf<FlashCard>()
        ('a'..'z').forEachIndexed { index, c ->
            flashCardsToInsert.add(
                FlashCard(
                    cardId = index,
                    groupId = 1,
                    question = c.toString(),
                    answer= ""
                )
            )
        }

        val exception = try {
            addMultipleCardsUseCase.invoke(flashCardsToInsert)
            null
        } catch (exception: InvalidFlashCardException){
            exception
        }

        assertEquals(exception?.message,"Any answer of the flashcard can't be empty.")

    }

    @Test
    fun testAddingMultipleFlashCardsWithoutOneAnswer() = runBlocking{

        val flashCardsToInsert = mutableListOf<FlashCard>()
        ('a'..'z').forEachIndexed { index, c ->
            flashCardsToInsert.add(
                FlashCard(
                    cardId = index,
                    groupId = 1,
                    question = c.toString(),
                    answer= c.toString()
                )
            )
        }

        flashCardsToInsert[flashCardsToInsert.size/2] = flashCardsToInsert[flashCardsToInsert.size/2].copy(
            answer = ""
        )

        val exception = try {
            addMultipleCardsUseCase.invoke(flashCardsToInsert)
            null
        } catch (exception: InvalidFlashCardException){
            exception
        }

        assertEquals(exception?.message,"Any answer of the flashcard can't be empty.")

    }
}