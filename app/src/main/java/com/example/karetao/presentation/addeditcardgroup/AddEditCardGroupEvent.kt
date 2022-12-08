package com.example.karetao.presentation.addeditcardgroup

import androidx.compose.ui.focus.FocusState

sealed class AddEditCardGroupEvent {
    data class EnteredGroupName(val value: String): AddEditCardGroupEvent()
    data class ChangeGroupNameFocus(val focusState: FocusState): AddEditCardGroupEvent()
    data class EnteredDescription(val value: String): AddEditCardGroupEvent()
    data class ChangeDescriptionFocus(val focusState: FocusState): AddEditCardGroupEvent()

    object SaveCardGroup: AddEditCardGroupEvent()
}