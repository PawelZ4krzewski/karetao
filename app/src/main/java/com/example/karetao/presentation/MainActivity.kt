package com.example.karetao.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.karetao.presentation.add_edit_flashcard.AddEditFlashCardScreen
import com.example.karetao.presentation.flashcards.FlashCardsScreen
import com.example.karetao.presentation.util.Screen
import com.example.karetao.ui.theme.KaretaoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaretaoTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.FlashCardScreen.route
                    ){
                        composable(route = Screen.FlashCardScreen.route) {
                            FlashCardsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditFlashCardScreen.route + "?cardId={cardId}",
                            arguments = listOf(
                                navArgument(
                                    name = "cardId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            AddEditFlashCardScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
