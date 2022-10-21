package com.example.karetao.presentation.add_edit_flashcard

import android.util.Log
import androidx.compose.runtime.State
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

    private val _flashCardQuestion = mutableStateOf(FlashCardTextFieldState(
        hint = "Enter question..."
    ))
    val flashCardQuestion: State<FlashCardTextFieldState> = _flashCardQuestion

    private val _flashCardAnswer = mutableStateOf(FlashCardTextFieldState(
        hint = "Enter answer..."
    ))
    val flashCardAnswer: State<FlashCardTextFieldState> = _flashCardAnswer

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentFlashCardId: Int? = null
    private var currentGroupId: Int? = null

    init {
        savedStateHandle.get<Int>("cardId")?.let { cardId ->
            Log.d("Add flashcard",cardId.toString())
            if(cardId != -1){
                viewModelScope.launch {
                    flashCardUseCases.getFlashCard(cardId)?.also {
                        currentFlashCardId = it.cardId

                        _flashCardQuestion.value = flashCardQuestion.value.copy(
                            text = it.question,
                            isHintVisible = false
                        )
                        _flashCardAnswer.value = flashCardAnswer.value.copy(
                            text = it.answer,
                            isHintVisible = false
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
                _flashCardQuestion.value = _flashCardQuestion.value.copy(
                    text = event.value
                )
            }
            is AddEditFlashCardEvent.ChangeQuestionFocus -> {
                _flashCardQuestion.value = _flashCardQuestion.value.copy(
                    isHintVisible = !event.focusState.isFocused && flashCardQuestion.value.text.isBlank()
                )
            }
            is AddEditFlashCardEvent.EnteredAnswer -> {
                _flashCardAnswer.value = _flashCardAnswer.value.copy(
                    text = event.value
                )
            }
            is AddEditFlashCardEvent.ChangeAnswerFocus -> {
                _flashCardAnswer.value = _flashCardAnswer.value.copy(
                    isHintVisible = !event.focusState.isFocused && flashCardAnswer.value.text.isBlank()
                )
            }
            is AddEditFlashCardEvent.SaveFlashCard -> {
                viewModelScope.launch {
                    try{
                        flashCardUseCases.addFlashCard(
                            FlashCard(
                                cardId = currentFlashCardId,
                                groupId = currentGroupId,
                                question = flashCardQuestion.value.text,
                                answer = flashCardAnswer.value.text
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

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveFlashCard: UiEvent()
    }
}