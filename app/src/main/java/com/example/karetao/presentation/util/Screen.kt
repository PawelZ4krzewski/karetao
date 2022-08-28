package com.example.karetao.presentation.util

sealed class Screen(val route: String){
    object FlashCardScreen: Screen("flashcard_screen")
    object AddEditFlashCardScreen: Screen("Add_edit_flashcard_screen")
}