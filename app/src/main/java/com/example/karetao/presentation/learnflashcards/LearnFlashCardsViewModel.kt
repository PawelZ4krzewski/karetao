package com.example.karetao.presentation.learnflashcards

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.flashCard.FlashCardOrderType
import com.example.karetao.data.use_case.flashCard.GetFlashCardsFromSameGroupUseCase
import com.example.karetao.data.use_case.userCard.UserCardOrderType
import com.example.karetao.data.use_case.userCard.UserCardUseCases
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidFlashCardException
import com.example.karetao.model.UserCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnFlashCardsViewModel @Inject constructor(
    private val userCardUseCase: UserCardUseCases,
    private val getFlashCardsFromSameGroupUseCase: GetFlashCardsFromSameGroupUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(LearnFlashCardState())
    val state: State<LearnFlashCardState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getUserCardJob: Job? = null

    private var getFlashCardsJob: Job? = null



    init {
        savedStateHandle.get<Int>("groupId")?.let { groupId ->
            Log.d("Add flashcard", "GroupId $groupId")
            getFlashCardsFromSameGroup(
                FlashCardOrderType.Question(OrderType.Ascending),
                groupId
            )
        }

        getFlashCardsFromSameGroup(
            UserCardOrderType.Status(OrderType.Ascending),
            state.value.username
        )
    }



    fun onEvent(event: LearnFlashCardEvent){
        when(event){
            is LearnFlashCardEvent.SaveUserCard -> {

                checkAnswerIsCorrect(event.flashCard, event.isCorrect)

                updateUserFlashCards(event.flashCard, event.isCorrect)

            }
            is LearnFlashCardEvent.LearnAgain -> {
                if(event.isEveryFlashcards){
                    _state.value = state.value.copy(
                        learningFlashCardSet = state.value.flashCards,
                        learnedFlashCard = emptyList(),
                        repeatedFlashCard = emptyList()
                    )
                }
                else{
                    _state.value = state.value.copy(
                        learningFlashCardSet = state.value.repeatedFlashCard,
                        learnedFlashCard = emptyList(),
                        repeatedFlashCard = emptyList()
                    )
                }
            }
        }
    }




    private fun getFlashCardsFromSameGroup(userCardOrderType: UserCardOrderType, username:String, ){
        getUserCardJob?.cancel()

        getUserCardJob  = userCardUseCase.getUserCardFromSameUser(username, userCardOrderType)
            .onEach { userCard ->
                _state.value = state.value.copy(
                    userCard = userCard,
                    username = username
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getFlashCardsFromSameGroup(flashCardOrder: FlashCardOrderType, groupId:Int){
        getFlashCardsJob?.cancel()

        getFlashCardsJob  = getFlashCardsFromSameGroupUseCase(groupId, flashCardOrder)
            .onEach { flashCards ->
                _state.value = state.value.copy(
                    flashCards = flashCards,
                    learningFlashCardSet = flashCards,
                    flashCardOrder = flashCardOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun checkAnswerIsCorrect(flashCard: FlashCard, isCorrect: Boolean) {
        Log.d("Learn-FlashCard","${flashCard}  ${isCorrect}")

        if(isCorrect){
            _state.value = state.value.copy(
                learnedFlashCard = state.value.learnedFlashCard + listOf(flashCard),
                learningFlashCardSet = state.value.learningFlashCardSet - flashCard
            )
        }
        else{
            _state.value = state.value.copy(
                repeatedFlashCard = state.value.repeatedFlashCard + listOf(flashCard),
                learningFlashCardSet = state.value.learningFlashCardSet - flashCard
            )
        }
    }

    private fun updateUserFlashCards(flashCard: FlashCard, isCorrect: Boolean){

        val currentUserCard: UserCard
        val existedUserCard: UserCard? =
            state.value.userCard.firstOrNull { it.cardId == flashCard.cardId && it.username == state.value.username }

        if(existedUserCard != null){

            val currentStatus: Int


            Log.d("Learn-FlashCard","Edit Existing Card")
            if(isCorrect){
                currentStatus = maxOf(existedUserCard.status -1, 0)
            }
            else{
                currentStatus = minOf(existedUserCard.status + 1, 5)
            }

            currentUserCard = UserCard(
                existedUserCard.username,
                existedUserCard.cardId,
                currentStatus,
                System.currentTimeMillis()
            )
            Log.d("Learn-FlashCard", "Change card with new status: $currentStatus | ${existedUserCard.cardId} ${existedUserCard.username}")
        } else {
            Log.d("Learn-FlashCard","Create New Card ${flashCard.cardId} state.value.username 5")
            currentUserCard = UserCard(
                cardId = flashCard.cardId!!,
                username = state.value.username,
                status = 5,
                lastEdit = System.currentTimeMillis()
            )
        }

        viewModelScope.launch {

            try{
                userCardUseCase.insertUserCard(
                    currentUserCard
                )

                _eventFlow.emit(UiEvent.SaveUserCard)
            }catch(e: InvalidFlashCardException){
                _eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        message = e.message ?: "Coulnd't save UserCard"
                    )
                )
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveUserCard: UiEvent()
    }
}