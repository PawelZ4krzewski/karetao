package com.example.karetao.presentation.util

sealed class Screen(val route: String){
    object CardGroupScreen: Screen("cardgroup_screen")
    object FlashCardScreen: Screen("flashcard_screen")
    object AddEditFlashCardScreen: Screen("Add_edit_flashcard_screen")
    object AddEditCardGroupScreen: Screen("Add_edit_cardGroup_screen")
}