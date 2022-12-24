package com.example.karetao.presentation.cardgroups

import OrderSectionCardGroup
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.karetao.model.CardGroupInformation
import com.example.karetao.presentation.flashcards.components.CardGroupItem
import com.example.karetao.presentation.util.Screen
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.White
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                backgroundColor = DarkBlue
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add card group")
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
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
//                    IconButton(
//                        onClick = {
//                            Log.d("BackIcon", "I will back!")
//                        },
//                        modifier = Modifier.width(50.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = White,
//                            modifier = Modifier.size(30.dp)
//                        )
//                    }
                    IconButton(
                        onClick = {
                            Log.d("CardGroup-Screen", state.cardGroupsInformation.size.toString())
                            viewModel.onEvent(CardGroupEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Sort",
                            tint = White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Box(modifier = Modifier
                    .padding(30.dp, 10.dp, 30.dp, 20.dp)
                    .fillMaxWidth()
                ){
                    Text(
                        text = "Yours CardGroups",
                        style = MaterialTheme.typography.h3,
                        color = White
                    )
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
            }

            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                ){

                    items(state.cardGroupsInformation){ cardGroupInformation: CardGroupInformation ->

                        Spacer(modifier = Modifier.height(16.dp))

                        CardGroupItem(
                            cardGroupInformation = cardGroupInformation,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Log.d("Card Group", cardGroupInformation.toString())
                                    navController.navigate(
                                        Screen.FlashCardScreen.route + "?groupId=${cardGroupInformation.cardGroup.groupId}&groupName=${cardGroupInformation.cardGroup.groupName}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(CardGroupEvent.DeleteCardGroup(cardGroupInformation.cardGroup))
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
}