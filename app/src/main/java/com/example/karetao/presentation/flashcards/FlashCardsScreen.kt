package com.example.karetao.presentation.flashcards

import OrderSection
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.karetao.model.FlashCard
import com.example.karetao.presentation.cardgroups.CardGroupEvent
import com.example.karetao.presentation.flashcards.components.FlashCardItem
import com.example.karetao.presentation.util.Screen
import com.example.karetao.ui.theme.Blue
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.White
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
            FloatingActionButton(
                onClick = {
                Log.d("FlashCard",state.flashCards.toString())
                navController.navigate(Screen.LearnFlashCardsScreen.route+"?groupId=${state.groupId}")
//                navController.navigate(Screen.AddEditFlashCardScreen.route+"?groupId=${state.groupId}")
            },
            backgroundColor = DarkBlue
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add flashcard")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(DarkBlue)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.width(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onEvent(FlashCardsEvent.ToggleOrderSection)
                        },
                        modifier = Modifier.width(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Sort",
                            tint = White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(30.dp, 10.dp, 30.dp, 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = state.groupName,
                        style = MaterialTheme.typography.h3,
                        color = White
                    )
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
            }

            Column( Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(state.flashCards){ flashCard: FlashCard ->
                        FlashCardItem(
                            flashCard = flashCard,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp, 5.dp)
                                .clickable {
                                    Log.d("FlashCard", flashCard.toString())
                                    navController.navigate(
                                        Screen.AddEditFlashCardScreen.route + "?cardId=${flashCard.cardId}&groupId=${flashCard.groupId}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(FlashCardsEvent.DeleteFlashCard(flashCard))
                                scope.launch {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        message = "Flashcard deleted",
                                        actionLabel = "Undo"
                                    )

                                    if(result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(FlashCardsEvent.RestoreFlashCard)
                                    }
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}