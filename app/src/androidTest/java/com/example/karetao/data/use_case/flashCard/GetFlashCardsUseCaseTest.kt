package com.example.karetao.data.use_case.flashCard

import androidx.test.services.events.run.TestRunEvent
import com.example.karetao.data.repository.FakeFlashCardRepository
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetFlashCardsUseCaseTest{

    private lateinit var getFlashCardsUseCase: GetFlashCardsUseCase
    private lateinit var fakeRepository: FakeFlashCardRepository


    @Before
    fun setUp(){
        fakeRepository = FakeFlashCardRepository()
        getFlashCardsUseCase = GetFlashCardsUseCase(fakeRepository)

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

        flashCardsToInsert.shuffle()

        runBlocking {
            fakeRepository.insertMultipleFlashCards(flashCardsToInsert)
        }
    }

    @Test
    fun orderFlashcardsByQuestionAscendingCorrectOrder() = runBlocking {
        val flashcards = getFlashCardsUseCase(FlashCardOrderType.Question(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            Assert.assertTrue(flashcards[i].question < flashcards[i+1].question)
        }
    }

    @Test
    fun orderFlashcardsByQuestionDescendingCorrectOrder() = runBlocking {
        val flashcards = getFlashCardsUseCase(FlashCardOrderType.Question(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            Assert.assertTrue(flashcards[i].question > flashcards[i+1].question)
        }
    }


    @Test
    fun orderFlashcardsByAnswerAscendingCorrectOrder() = runBlocking {
        val flashcards = getFlashCardsUseCase(FlashCardOrderType.Answer(OrderType.Ascending)).first()

        for(i in 0..flashcards.size - 2) {
            Assert.assertTrue(flashcards[i].answer < flashcards[i+1].answer)
        }
    }

    @Test
    fun orderFlashcardsByAnswerDescendingCorrectOrder() = runBlocking {
        val flashcards = getFlashCardsUseCase(FlashCardOrderType.Answer(OrderType.Descending)).first()

        for(i in 0..flashcards.size - 2) {
            Assert.assertTrue(flashcards[i].answer > flashcards[i+1].answer)
        }
    }

}