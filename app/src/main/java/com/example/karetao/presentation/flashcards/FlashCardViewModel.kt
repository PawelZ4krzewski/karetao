package com.example.karetao.presentation.flashcards

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.flashCard.FlashCardOrderType
import com.example.karetao.data.use_case.flashCard.FlashCardUseCases
import com.example.karetao.model.FlashCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val flashCardUseCases: FlashCardUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(FlashCardState())
    val state: State<FlashCardState> = _state

    private var recentlyDeletedFlashCard: FlashCard? = null

    private var getFlashCardsJob: Job? = null

    init {
//        getFlashCards(FlashCardOrderType.Question(OrderType.Ascending))
        savedStateHandle.get<Int>("groupId")?.let { groupId ->
            Log.d("FlashCardScreen", "GroupId $groupId")
            getFlashCardsFromSameGroup(
                FlashCardOrderType.Question(OrderType.Ascending),
                groupId
            )
        }

        savedStateHandle.get<String>("groupName")?.let { groupName ->
            Log.d("FlashCardScreen", "Group Name $groupName")
            _state.value = state.value.copy(
                groupName = groupName
            )
        }

    }

    fun onEvent(event: FlashCardsEvent){
        when(event){
            is FlashCardsEvent.Order -> {
                if (state.value.flashCardOrder::class == event.flashCardOrder::class &&
                    state.value.flashCardOrder.orderType == event.flashCardOrder.orderType
                ){
                    return
                }
                getFlashCardsFromSameGroup(event.flashCardOrder, state.value.groupId)
            }
            is FlashCardsEvent.DeleteFlashCard -> {
                viewModelScope.launch {
                    flashCardUseCases.deleteFlashCard(event.flashCard)
                    recentlyDeletedFlashCard = event.flashCard
                }
            }
            is FlashCardsEvent.RestoreFlashCard -> {
                viewModelScope.launch {
                    flashCardUseCases.addFlashCard(recentlyDeletedFlashCard ?: return@launch)
                    recentlyDeletedFlashCard = null
                }
            }
            is FlashCardsEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getFlashCards(flashCardOrder: FlashCardOrderType){
        getFlashCardsJob?.cancel()
        getFlashCardsJob  = flashCardUseCases.getFlashCards(flashCardOrder)
            .onEach { flashCards ->
                _state.value = state.value.copy(
                    flashCards = flashCards,
                    flashCardOrder = flashCardOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getFlashCardsFromSameGroup(flashCardOrder: FlashCardOrderType, groupId:Int){
        getFlashCardsJob?.cancel()

        getFlashCardsJob  = flashCardUseCases.getFlashCardsFromSameGroupUseCase(groupId, flashCardOrder)
            .onEach { flashCards ->
                _state.value = state.value.copy(
                    groupId = groupId,
                    flashCards = flashCards,
                    flashCardOrder = flashCardOrder
                )
            }
            .launchIn(viewModelScope)
    }
}