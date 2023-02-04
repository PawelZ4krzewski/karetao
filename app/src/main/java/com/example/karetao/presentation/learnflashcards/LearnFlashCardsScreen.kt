package com.example.karetao.presentation.learnflashcards

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.Gray
import com.example.karetao.ui.theme.White
import java.util.*


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
    val backgroundColor = Gray

    var dialogState = state.learningFlashCardSet.isEmpty()

    val states = state.learningFlashCardSet.reversed()
        .map { it to rememberSwipeableCardState() }


    val isFlashCard = true

    Scaffold(
        scaffoldState = scaffoldState
    ){
        FinishDialog(
            dialogState = dialogState,
            onDismissRequest = { dialogState = !it },
            navController = navController,
            viewModel = viewModel,
        )
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkBlue)
                        .background(DarkBlue)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .weight(0.33F),
                        ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White,
                            modifier = Modifier
                                .size(30.dp)
                                .offset((-30).dp)
                        )
                    }
                    Row(modifier = Modifier
                        .weight(0.33F),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = state.repeatedFlashCard.size.toString(),
                            style = MaterialTheme.typography.h4,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold

                        )
                        Text(
                            text = state.learningFlashCardSet.size.toString(),
                            style = MaterialTheme.typography.h3,
                            color = White,
                            fontWeight = FontWeight.Bold


                        )
                        Text(
                            text = state.learnedFlashCard.size.toString(),
                            style = MaterialTheme.typography.h4,
                            color = Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(modifier = Modifier
                        .weight(0.33F)
                    )



            }

            Spacer(Modifier.height(60.dp))

            Box(
                Modifier
//                    .background(Color.Blue, RoundedCornerShape(40.dp))
                    .padding(20.dp)
                    .fillMaxSize()
                    .align(CenterHorizontally)
            ) {
                states.forEach { (flashCard, swipeableCardState) ->
                    if (swipeableCardState.swipedDirection == null) {


                        if(isFlashCard){
                            WritingFlashCard(
                                flashCard.question,
                                flashCard.answer,
                                modifier = Modifier
                                    .background(White, RoundedCornerShape(20.dp))
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.9f)
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

                                            }

                                            if (it == Direction.Right) {
                                                Log.d("Swipeable-Card", "SWIPE RIGHT")

                                                viewModel.onEvent(
                                                    LearnFlashCardEvent.SaveUserCard(
                                                        flashCard,
                                                        true
                                                    )
                                                )
                                            }
                                        },
                                        onSwipeCancel = {
                                            Log.d("Swipeable-Card", "Cancelled swipe")
                                        }
                                    )
                            )
                        }
                        else {

                            var cardFace by remember {
                                mutableStateOf(CardFace.Front)
                            }

                            FlipCard(
                                cardFace = cardFace,
                                onClick = { cardFace = cardFace.next },
                                modifier = Modifier
//                                .background(Color.Red, RoundedCornerShape(40.dp))
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

                                            }

                                            if (it == Direction.Right) {
                                                Log.d("Swipeable-Card", "SWIPE RIGHT")

                                                viewModel.onEvent(
                                                    LearnFlashCardEvent.SaveUserCard(
                                                        flashCard,
                                                        true
                                                    )
                                                )
                                            }
                                        },
                                        onSwipeCancel = {
                                            Log.d("Swipeable-Card", "Cancelled swipe")
                                        }
                                    ),
                                front = {
                                    Box(
                                        modifier = Modifier
//                                        .background(Color.Green, RoundedCornerShape(40.dp))
                                            .fillMaxSize(),
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
                                        modifier = Modifier
//                                        .background(Color.Yellow, RoundedCornerShape(40.dp))
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = flashCard.answer,
                                            style = MaterialTheme.typography.h3,
                                        )
                                    }
                                },
                            )
                        }
                    }
                    LaunchedEffect(flashCard, swipeableCardState.swipedDirection) {
                        if (swipeableCardState.swipedDirection != null) {
                            println("The card was swiped to ${swipeableCardState.swipedDirection!!}")
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
//            .background(Color.Magenta, RoundedCornerShape(40.dp))
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
                Modifier
                    .fillMaxSize()
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

//@Preview
//@Composable
//fun WritingFlashCard(
////    modifier: Modifier
//){
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//                text = "CORRECT!",
//                style = MaterialTheme.typography.h3
//            )
//
//        Text(
//            text = "Question",
//            style = MaterialTheme.typography.h3
//        )
//        AnimatedVisibility(
//            visible = true,
//            enter = fadeIn() + slideInVertically(),
//            exit = fadeOut() + slideOutVertically()
//        ){
//            Text(
//                text = "Answer",
//                style = MaterialTheme.typography.h3
//            )
//        }
//
//    }
//}

@Composable
fun WritingFlashCard(
    question: String,
    answer: String,
    modifier: Modifier
){
    val typedAnswer = remember { mutableStateOf("") }
    val underlineColor = remember {
        mutableStateOf(Color.Black)
    }

    val correctHidden = remember {
        mutableStateOf(true)
    }

    Box(
        modifier = modifier
            .background(White, RoundedCornerShape(20.dp))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                horizontalAlignment = CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.15f)
                )
                Text(
                    text = question,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold
                )

                AnimatedVisibility(
                    visible = !correctHidden.value,
                    enter = fadeIn()
                )
//              if(!correctHidden.value)
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        horizontalAlignment = CenterHorizontally
                    )
                    {
                        Text(
                            text = answer,
                            style = MaterialTheme.typography.h4,
    //                    fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier
                                .height(4.dp)
                                .fillMaxWidth(0.45f)
                                .background(underlineColor.value)
                        )

                        Text(
                            text = "CORRECT ANSWER",
                            style = MaterialTheme.typography.body1,
    //                    fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    BasicTextField(
                        value = typedAnswer.value,
                        onValueChange = { typedAnswer.value = it },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.h5,
                        decorationBox = { innerTextField ->
                            if (typedAnswer.value.isEmpty()) {
                                Text(
                                    text = "Your Answer",
                                    style = MaterialTheme.typography.h5,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        },
                    modifier = Modifier
                        .weight(1f)
                    )

                    IconButton(
                        onClick = {
                                  if(isAnwerCorrect(answer, typedAnswer.value)){
                                      underlineColor.value = Color.Green
                                  }
                                  else{
                                      underlineColor.value = Color.Red
                                  }
                            correctHidden.value = false
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Check Answer",
                            tint = DarkBlue,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }


                Spacer(
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            bottom = 5.dp
                        )
                        .height(4.dp)
                        .fillMaxWidth()
                        .background(DarkBlue)
                )
                Text(
                    text = "TYPE THE ANSWER"
                )
            }
        }
    }
}


@Composable
fun FinishDialog(
    dialogState: Boolean,
    onDismissRequest: (dialogState: Boolean) -> Unit,
    navController: NavController,
    viewModel: LearnFlashCardsViewModel
){
    if(dialogState){
        AlertDialog(
            onDismissRequest = {
                onDismissRequest(dialogState)
            },
            backgroundColor = DarkBlue,
            title = null,
            text= null,
            buttons = {
                Column(
                    Modifier
                        .fillMaxHeight(0.8F)
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            top = 20.dp,
                            end = 20.dp,
//                            bottom = 0.dp
                        )
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                   Text(
                       text = "Flashcard set is empty!",
                       style = MaterialTheme.typography.h5,
                       fontWeight = FontWeight.Bold,
                       color = White
                   )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text(
                            text = viewModel.state.value.repeatedFlashCard.size.toString(),
                            style = MaterialTheme.typography.h4,
                            color = Color.Red,
                        )
                        Text(
                            text = viewModel.state.value.flashCards.size.toString(),
                            style = MaterialTheme.typography.h3,
                            color = Color.White,
                        )
                        Text(
                            text = viewModel.state.value.learnedFlashCard.size.toString(),
                            style = MaterialTheme.typography.h4,
                            color = Color.Green,
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(LearnFlashCardEvent.LearnAgain(true))
                            onDismissRequest(dialogState)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)

                    ) {
                        Text(
                            text = "LEARN FULL SET",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(LearnFlashCardEvent.LearnAgain(false))
                            onDismissRequest(dialogState)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        shape = RoundedCornerShape(8.dp),
                        enabled = viewModel.state.value.repeatedFlashCard.isNotEmpty(),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "LEARN FAILURES",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            onDismissRequest(dialogState)
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp)
                    ) {
                        Text(
                            text = "BACK",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = White
                        )
                    }
                }
            },
            properties =  DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
            shape = RoundedCornerShape(10.dp)
        )
    }
}

fun isAnwerCorrect(
    answer: String,
    userAnswer:String
): Boolean
{
    return answer.lowercase() == userAnswer.lowercase()
}