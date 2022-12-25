package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repository.FakeFlashCardRepository
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetFlashCardAmountTest{

    private lateinit var getFlashCardAmount: GetFlashCardAmount
    private lateinit var fakeRepository: FakeFlashCardRepository

    @Before
    fun setUp(){
        fakeRepository = FakeFlashCardRepository()
        getFlashCardAmount = GetFlashCardAmount(fakeRepository)

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

        runBlocking {
            fakeRepository.insertMultipleFlashCards(flashCardsToInsert)
        }
    }

    @Test
    fun getFlashCardAmountTest() = runBlocking{
        val flashCardAmountFromRepository = fakeRepository.getFlashCards().first().size
        val flashCardAmount = getFlashCardAmount.invoke(1)

        assertEquals(flashCardAmountFromRepository, flashCardAmount)
    }
}