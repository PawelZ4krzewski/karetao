package com.example.karetao.presentation.cardgroups

import OrderSectionCardGroup
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
import com.example.karetao.model.CardGroup
import com.example.karetao.presentation.flashcards.FlashCardsEvent
import com.example.karetao.presentation.flashcards.components.CardGroupItem
import com.example.karetao.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun CardGroupsScreen(
    navController: NavController,
    viewModel: CardGroupViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditCardGroupScreen.route)
            },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add card group")
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
                    text = "Your Card Groups",
                    style = MaterialTheme.typography.h4
                )
                IconButton(onClick = {
                    viewModel.onEvent(CardGroupEvent.ToggleOrderSection)
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
                OrderSectionCardGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    cardGroupOrderType = state.cardGroupOrder,
                    onOrderChange = {
                        viewModel.onEvent(CardGroupEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.cardGroups){ cardGroup: CardGroup ->
                    CardGroupItem(
                        cardGroup = cardGroup,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Log.d("Card Group",cardGroup.toString())
                                navController.navigate(
                                    Screen.FlashCardScreen.route+"?groupId=${cardGroup.groupId}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(CardGroupEvent.DeleteCardGroup(cardGroup))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Flashcard deleted",
                                    actionLabel = "Undo"
                                )

                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(CardGroupEvent.RestoreCardGroups)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}