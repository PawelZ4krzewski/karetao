package com.example.karetao.data.use_case.flashCard

import com.example.karetao.data.repository.FakeFlashCardRepository
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.model.FlashCard
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetFlashCardsFromSameGroupUseCaseTest{

private lateinit var getFlashCardsFromSameGroupUseCase: GetFlashCardsFromSameGroupUseCase
private lateinit var fakeRepository: FakeFlashCardRepository


@Before
fun setUp(){
    fakeRepository = FakeFlashCardRepository()
    getFlashCardsFromSameGroupUseCase = GetFlashCardsFromSameGroupUseCase(fakeRepository)

    val flashCardsToInsert = mutableListOf<FlashCard>()
    ('a'..'z').forEachIndexed { index, c ->
        flashCardsToInsert.add(
            FlashCard(
                cardId = index,
                groupId = index%2,
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
fun orderFlashcardsByQuestionAscendingCorrectOrderFromGroupId1() = runBlocking {
    val flashcards = getFlashCardsFromSameGroupUseCase(1, FlashCardOrderType.Question(OrderType.Ascending)).first()

    for(i in 0..flashcards.size - 2) {
        assertTrue(flashcards[i].question < flashcards[i+1].question)
        assertTrue(flashcards[i].groupId == 1)
    }
}

@Test
fun orderFlashcardsByQuestionDescendingCorrectOrderFromGroupId1() = runBlocking {
    val flashcards = getFlashCardsFromSameGroupUseCase(1, FlashCardOrderType.Question(OrderType.Descending)).first()

    for(i in 0..flashcards.size - 2) {
        assertTrue(flashcards[i].question > flashcards[i+1].question)
        assertTrue(flashcards[i].groupId == 1)
    }
}


@Test
fun orderFlashcardsByAnswerAscendingCorrectOrderFromGroupId1() = runBlocking {
    val flashcards = getFlashCardsFromSameGroupUseCase(1, FlashCardOrderType.Answer(OrderType.Ascending)).first()

    for(i in 0..flashcards.size - 2) {
        assertTrue(flashcards[i].answer < flashcards[i+1].answer)
        assertTrue(flashcards[i].groupId == 1)
    }
}

@Test
fun orderFlashcardsByAnswerDescendingCorrectOrderFromGroupId1() = runBlocking {
    val flashcards = getFlashCardsFromSameGroupUseCase(1, FlashCardOrderType.Answer(OrderType.Descending)).first()

    for(i in 0..flashcards.size - 2) {
        assertTrue(flashcards[i].answer > flashcards[i+1].answer)
        assertTrue(flashcards[i].groupId == 1)
    }
}
}