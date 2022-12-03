package com.example.karetao.presentation.LearnFlashCards

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.flashCard.FlashCardOrderType
import com.example.karetao.data.use_case.flashCard.FlashCardUseCases
import com.example.karetao.data.use_case.userCard.UserCardOrderType
import com.example.karetao.data.use_case.userCard.UserCardUseCases
import com.example.karetao.model.InvalidFlashCardException
import com.example.karetao.model.UserCard
import com.example.karetao.presentation.add_edit_flashcard.AddEditFlashCardViewModel
import com.example.karetao.presentation.flashcards.FlashCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LearnFlashCardsViewModel @Inject constructor(
    private val userCardUseCase: UserCardUseCases,
    private val flashCardUseCases: FlashCardUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(LearnFlashCardState())
    val state: State<LearnFlashCardState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getUserCardJob: Job? = null

    private var getFlashCardsJob: Job? = null

    private var currentStatus: Int? = null

    private var currentUserCard: UserCard? = null

    private var existedUserCard: UserCard? = null

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



    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: LearnFlashCardEvent){
        when(event){
            is LearnFlashCardEvent.SaveUserCard -> {
                Log.d("Learn-FlashCard","${event.flashCard}  ${event.isCorrect}")

                if(event.isCorrect){
                    _state.value = state.value.copy(
                        learnedFlashCard = state.value.learnedFlashCard + listOf(event.flashCard),
                        learningFlashCardSet = state.value.learningFlashCardSet - event.flashCard
                    )
                }
                else{
                    _state.value = state.value.copy(
                        repeatedFlashCard = state.value.repeatedFlashCard + listOf(event.flashCard),
                        learningFlashCardSet = state.value.learningFlashCardSet - event.flashCard
                    )
                }






                try{
                    existedUserCard = state.value.userCard.filter { it.cardId == event.flashCard.cardId && it.username == state.value.username}[0]
                }catch(e: Exception){
                    Log.d("Learn-FlashCard","UserCard doesn't exist yet. Official Error: " + e.toString())
                }

                if(existedUserCard != null){
                    Log.d("Learn-FlashCard","Edit Existing Card")
                    if(event.isCorrect){
                        currentStatus = maxOf(existedUserCard!!.status -1, 0)
                    }
                    else{
                        currentStatus = minOf(existedUserCard!!.status + 1, 5)
                    }

                    currentUserCard = UserCard(
                        existedUserCard!!.username,
                        existedUserCard!!.cardId,
                        existedUserCard!!.status
                    )

                } else {
                    Log.d("Learn-FlashCard","Create New Card")
                    currentUserCard = UserCard(
                        cardId = event.flashCard.cardId!!,
                        username = state.value.username,
                        status = 5
                    )
                }

                viewModelScope.launch {

                    try{
                        userCardUseCase.insertUserCard(
                            currentUserCard!!
                        )

                        _eventFlow.emit(UiEvent.SaveUserCard)
                    }catch(e: InvalidFlashCardException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Coulnd't save UserCard"
                            )
                        )
                    }
                    currentUserCard = null
                }
            }
            is LearnFlashCardEvent.LearnAgain -> {
                if(event.everyFlashcards){
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

        getFlashCardsJob  = flashCardUseCases.getFlashCardsFromSameGroupUseCase(groupId, flashCardOrder)
            .onEach { flashCards ->
                _state.value = state.value.copy(
                    flashCards = flashCards,
                    learningFlashCardSet = flashCards,
                    flashCardOrder = flashCardOrder
                )
            }
            .launchIn(viewModelScope)
    }



    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveUserCard: UiEvent()
        object LearnAgain: UiEvent()
    }
}