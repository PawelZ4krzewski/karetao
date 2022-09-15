package com.example.karetao.presentation.add_edit_cardgroup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karetao.data.use_case.cardGroup.CardGroupUseCases
import com.example.karetao.model.CardGroup
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidCardGroupException
import com.example.karetao.model.InvalidFlashCardException
import com.example.karetao.presentation.add_edit_flashcard.AddEditFlashCardEvent
import com.example.karetao.presentation.add_edit_flashcard.FlashCardTextFieldState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCardGroupViewModel @Inject constructor(
    private val cardGroupUseCase: CardGroupUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _CardGroupName = mutableStateOf(
        CardGroupTextFieldState(
        hint = "Enter name..."
        )
    )
    val cardGroupName: State<CardGroupTextFieldState> = _CardGroupName

    private val _CardGroupDescription = mutableStateOf(
        CardGroupTextFieldState(
        hint = "Enter description..."
        )
    )

    val cardGroupDescription: State<CardGroupTextFieldState> = _CardGroupDescription

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentCardGroupId: Int? = null

    init {
        savedStateHandle.get<Int>("groupId")?.let { groupId ->
            Log.d("Add cardgroup",groupId.toString())
            if(groupId != -1){
                viewModelScope.launch {
                    cardGroupUseCase.getCardGroup(groupId)?.also {
                        currentCardGroupId = it.groupId

                        _CardGroupName.value = cardGroupName.value.copy(
                            text = it.groupName,
                            isHintVisible = false
                        )
                        _CardGroupDescription.value = cardGroupDescription.value.copy(
                            text = it.description,
                            isHintVisible = false
                        )
                    }
                }
            }

        }
    }

    fun onEvent(event: AddEditCardGroupEvent){
        when(event){
            is AddEditCardGroupEvent.EnteredGroupName -> {
                _CardGroupName.value = cardGroupName.value.copy(
                    text = event.value
                )
            }
            is AddEditCardGroupEvent.ChangeGroupNameFocus -> {
                _CardGroupName.value = cardGroupName.value.copy(
                    isHintVisible = !event.focusState.isFocused && cardGroupName.value.text.isBlank()
                )
            }
            is AddEditCardGroupEvent.EnteredDescription -> {
                _CardGroupDescription.value = cardGroupDescription.value.copy(
                    text = event.value
                )
            }
            is AddEditCardGroupEvent.ChangeDescriptionFocus -> {
                _CardGroupDescription.value = cardGroupDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused && cardGroupDescription.value.text.isBlank()
                )
            }
            is AddEditCardGroupEvent.SaveCardGroup -> {
                viewModelScope.launch {
                    try{
                        cardGroupUseCase.addCardGroup(
                            CardGroup(
                                groupId = currentCardGroupId,
                                groupName = cardGroupName.value.text,
                                description = cardGroupDescription.value.text
                            )
                        )
                        Log.d("Add cardGroup", currentCardGroupId.toString())
                        _eventFlow.emit(UiEvent.SaveCardGroup)
                    }catch(e: InvalidCardGroupException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Coulnd't save card group"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveCardGroup: UiEvent()
    }
}