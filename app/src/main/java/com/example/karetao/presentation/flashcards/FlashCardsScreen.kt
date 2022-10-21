package com.example.karetao.presentation.flashcards

import OrderSection
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.karetao.model.FlashCard
import com.example.karetao.presentation.flashcards.components.FlashCardItem
import com.example.karetao.presentation.util.Screen

@ExperimentalAnimationApi
@Composable
fun FlashCardsScreen(
    navController: NavController,
    viewModel: FlashCardViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditFlashCardScreen.route+"?groupId=${state.groupId}")
            },
            backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add flashcard")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Your flashcards",
                    style = MaterialTheme.typography.h4
                )
                IconButton(onClick = {
                    viewModel.onEvent(FlashCardsEvent.ToggleOrderSection)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Sort"
                    )

                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    flashCardOrderType = state.flashCardOrder,
                    onOrderChange = {
                        viewModel.onEvent(FlashCardsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.flashCards){ flashCard: FlashCard ->
                    FlashCardItem(
                        flashCard = flashCard,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Log.d("FlashCard",flashCard.toString())
                                navController.navigate(
                                    Screen.AddEditFlashCardScreen.route+"?cardId=${flashCard.cardId}&groupId=${flashCard.groupId}"
                                )
                            }
                    )
                }
            }
        }
    }
}