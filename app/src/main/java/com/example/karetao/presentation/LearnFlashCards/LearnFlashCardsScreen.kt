package com.example.karetao.presentation.LearnFlashCards

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.karetao.model.FlashCard
import com.example.karetao.presentation.flashcards.FlashCardViewModel
import com.example.karetao.presentation.flashcards.FlashCardsEvent
import com.example.karetao.presentation.flashcards.components.FlashCardItem
import com.example.karetao.presentation.util.Screen
import com.example.karetao.ui.theme.DarkBlue
import kotlinx.coroutines.launch


@OptIn(ExperimentalSwipeableCardApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun LearnFlashCardsScreen(
    navController: NavController,
    viewModel: LearnFlashCardsViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val stateSwipeableCard = rememberSwipeableCardState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("Learn FlashCard",state.flashCards.toString())
                Log.d("Learn FlashCard",state.userCard.toString())
            },
                backgroundColor = DarkBlue
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add flashcard")
            }
        },
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            var hint by remember {
                mutableStateOf("Swipe a card or press a button below")
            }

            val states = state.flashCards.reversed()
                .map { it to rememberSwipeableCardState() }

            Box(Modifier
                .padding(24.dp)
                .fillMaxSize()
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally)) {
                states.forEach { (flashCard, state) ->
                    if (state.swipedDirection == null) {
                        ProfileCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .swipableCard(
                                    state = state,
                                    blockedDirections = listOf(Direction.Down),
                                    onSwiped = {
                                        // swipes are handled by the LaunchedEffect
                                        // so that we track button clicks & swipes
                                        // from the same place
                                    },
                                    onSwipeCancel = {
                                        Log.d("Swipeable-Card", "Cancelled swipe")
                                    }
                                ),
                            flashCard = flashCard
                        )
                    }
                    LaunchedEffect(flashCard, state.swipedDirection) {
                        if (state.swipedDirection != null) {
                            hint = "You swiped ${state.swipedDirection}"
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier,
    flashCard: FlashCard
) {
    Card(modifier) {
        Box {
            Scrim(Modifier.align(Alignment.BottomCenter))
            Column(Modifier.align(Alignment.BottomStart)) {
                Text(text = flashCard.question,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
private fun Hint(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(modifier
        .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
        .height(180.dp)
        .fillMaxWidth())
}