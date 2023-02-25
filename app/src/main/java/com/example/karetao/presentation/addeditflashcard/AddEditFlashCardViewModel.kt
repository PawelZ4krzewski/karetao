package com.example.karetao.presentation.addeditflashcard

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karetao.data.use_case.flashCard.FlashCardUseCases
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidFlashCardException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFlashCardViewModel @Inject constructor(
    private val flashCardUseCases: FlashCardUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<AddFlashCardState> = mutableStateOf(AddFlashCardState())
    val state: MutableState<AddFlashCardState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentFlashCardId: Int? = null
    private var currentGroupId: Int? = null

    init {
        savedStateHandle.get<Int>("cardId")?.let { cardId ->
            Log.d("Add flashcard",cardId.toString())
            if(cardId != -1){
                currentFlashCardId = cardId
                viewModelScope.launch {
                    flashCardUseCases.getFlashCard(cardId)?.also {
                        _state.value = state.value.copy(
                            addFlashCards = listOf(AddFlashCardItemValues(
                                flashCardQuestionText = it.question,
                                isFlashCardQuestionVisible = false,
                                flashCardAnswerText = it.answer,
                                isFlashCardAnswerVisible = false
                            ))
                        )
                    }
                }
            }
        }

        savedStateHandle.get<Int>("groupId")?.let { groupId ->

            Log.d("Add flashcard GroupID",groupId.toString())
            if(groupId != -1){
                currentGroupId = groupId
            }

        }
    }

    fun onEvent(event: AddEditFlashCardEvent){
        when(event){
            is AddEditFlashCardEvent.EnteredQuestion -> {
                _state.value = state.value.copy(
                    addFlashCards = state.value.addFlashCards.mapIndexed { index, addFlashCardItem ->
                        if (event.id == index) {
                            addFlashCardItem.copy(flashCardQuestionText = event.value)
                        } else {
                            addFlashCardItem
                        }
                    }
                )
            }
            is AddEditFlashCardEvent.ChangeQuestionFocus -> {
                _state.value = state.value.copy(
                    addFlashCards = state.value.addFlashCards.mapIndexed { index, addFlashCardItem ->
                        if (event.id == index) {
                            addFlashCardItem.copy(isFlashCardQuestionVisible = !event.focusState.isFocused && addFlashCardItem.flashCardQuestionText.isBlank())
                        } else {
                            addFlashCardItem
                        }
                    }
                )
            }
            is AddEditFlashCardEvent.EnteredAnswer -> {
                _state.value = state.value.copy(
                    addFlashCards = state.value.addFlashCards.mapIndexed { index, addFlashCardItem ->
                        if (event.id == index) {
                            addFlashCardItem.copy(flashCardAnswerText = event.value)
                        } else {
                            addFlashCardItem
                        }
                    }
                )
            }
            is AddEditFlashCardEvent.ChangeAnswerFocus -> {

                _state.value = state.value.copy(
                    addFlashCards = state.value.addFlashCards.mapIndexed { index, addFlashCardItem ->
                        if (event.id == index) {
                            addFlashCardItem.copy(isFlashCardAnswerVisible = !event.focusState.isFocused && addFlashCardItem.flashCardAnswerText.isBlank())
                        } else {
                            addFlashCardItem
                        }
                    }
                )

            }
            is AddEditFlashCardEvent.AddNewFlashCardItem -> {

                _state.value = state.value.copy(
                    addFlashCards = state.value.addFlashCards + AddFlashCardItemValues()
                )

                var number: Int? = null

                number?.let {
                    number = it + 1
                }

            }
            is AddEditFlashCardEvent.DeleteFlashCardItem -> {
                _state.value = state.value.copy(
                    addFlashCards = state.value.addFlashCards - event.item
                )
            }
            is AddEditFlashCardEvent.SaveFlashCard -> {
                viewModelScope.launch {
                    var flashCardIndex: Int?
                    state.value.addFlashCards.forEachIndexed {index, it ->
                        try{
                            flashCardIndex = null
                            if(index == 0){
                                flashCardIndex = currentFlashCardId
                            }

                            flashCardUseCases.addFlashCard(
                                FlashCard(
                                    cardId = flashCardIndex,
                                    groupId = currentGroupId,
                                    question = it.flashCardQuestionText,
                                    answer = it.flashCardAnswerText
                                )
                            )
                            Log.d("Add flashcard", currentFlashCardId.toString())
                            _eventFlow.emit(UiEvent.SaveFlashCard)
                        }catch(e: InvalidFlashCardException){
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = e.message ?: "Coulnd't save flashcard"
                                )
                            )
                        }
                    }
                }
            }
        }
    }




    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveFlashCard: UiEvent()
    }
}

