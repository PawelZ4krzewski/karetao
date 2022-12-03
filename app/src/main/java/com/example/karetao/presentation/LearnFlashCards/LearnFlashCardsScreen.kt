package com.example.karetao.presentation.LearnFlashCards

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.FloatingWindow
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
import com.example.karetao.ui.theme.Gray
import com.example.karetao.ui.theme.White
import kotlinx.coroutines.launch


@OptIn(ExperimentalSwipeableCardApi::class, ExperimentalMaterialApi::class)
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
    var backgroundColor = Gray

    Scaffold(
        scaffoldState = scaffoldState
    ){
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            val states = state.flashCards.reversed()
                .map { it to rememberSwipeableCardState() }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkBlue)
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .width(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        text = state.repeatedFlashCard.size.toString(),
                        style = MaterialTheme.typography.h4,
                        color = Color.Red

                    )
                    Text(
                        text = state.learningFlashCardSet.size.toString(),
                        style = MaterialTheme.typography.h3,
                        color = White

                    )
                    Text(
                        text = state.learnedFlashCard.size.toString(),
                        style = MaterialTheme.typography.h4,
                        color = Color.Green
                    )




            }

            Box(
                Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                states.forEach { (flashCard, swipeableCardState) ->
                    if (swipeableCardState.swipedDirection == null) {

                        var cardFace by remember {
                            mutableStateOf(CardFace.Front)
                        }

                        FlipCard(
                            cardFace = cardFace,
                            onClick = { cardFace = cardFace.next },
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .swipableCard(
                                    state = swipeableCardState,
                                    blockedDirections = listOf(Direction.Down, Direction.Up),
                                    onSwiped = {
                                        if (it == Direction.Left) {
                                            Log.d("Swipeable-Card", "SWIPE LEFT")

                                            viewModel.onEvent(
                                                LearnFlashCardEvent.SaveUserCard(
                                                    flashCard,
                                                    false
                                                )
                                            )

                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Try one more time later!"
                                                )
                                            }
                                        }

                                        if (it == Direction.Right) {
                                            Log.d("Swipeable-Card", "SWIPE RIGHT")

                                            viewModel.onEvent(
                                                LearnFlashCardEvent.SaveUserCard(
                                                    flashCard,
                                                    true
                                                )
                                            )

                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "You know it!"
                                                )
                                            }
                                        }
                                    },
                                    onSwipeCancel = {
                                        Log.d("Swipeable-Card", "Cancelled swipe")
                                    }
                                ),
                            front = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = flashCard.question,
                                        style = MaterialTheme.typography.h3,
                                    )
                                }
                            },
                            back = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = flashCard.question,
                                        style = MaterialTheme.typography.h3,
                                    )
                                }
                            },
                        )

                    }
                    LaunchedEffect(flashCard, swipeableCardState.swipedDirection) {
                        if (swipeableCardState.swipedDirection != null) {
                            println("The card was swiped to ${swipeableCardState.swipedDirection!!}")
                            if(state.learningFlashCardSet.size == 0 ){

                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


enum class CardFace(val angle: Float) {
    Front(0f) {
        override val next: CardFace
            get() = Back
    },
    Back(180f) {
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

enum class RotationAxis {
    AxisX,
    AxisY,
}

@ExperimentalMaterialApi
@Composable
fun FlipCard(
    cardFace: CardFace,
    onClick: (CardFace) -> Unit,
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing,
        )
    )
    Card(
        onClick = { onClick(cardFace) },
        modifier = modifier
            .graphicsLayer {
                if (axis == RotationAxis.AxisX) {
                    rotationX = rotation.value
                } else {
                    rotationY = rotation.value
                }
                cameraDistance = 12f * density
            },
    ) {
        if (rotation.value <= 90f) {
            Box(
                Modifier.fillMaxSize()
            ) {
                front()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        if (axis == RotationAxis.AxisX) {
                            rotationX = 180f
                        } else {
                            rotationY = 180f
                        }
                    },
            ) {
                back()
            }
        }
    }
}