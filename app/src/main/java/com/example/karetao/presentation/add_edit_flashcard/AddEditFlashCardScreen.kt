package com.example.karetao.presentation.add_edit_flashcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.karetao.presentation.add_edit_cardgroup.AddEditCardGroupEvent
import com.example.karetao.presentation.add_edit_flashcard.components.AddFlashCardItem
import com.example.karetao.presentation.add_edit_flashcard.components.TransparentHintTextField
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.White
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditFlashCardScreen(
    navController: NavController,
    viewModel: AddEditFlashCardViewModel = hiltViewModel()
) {
    val questionState = viewModel.flashCardQuestion.value
    val answerState = viewModel.flashCardAnswer.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()
    
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
               is AddEditFlashCardViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
               }
               is AddEditFlashCardViewModel.UiEvent.SaveFlashCard -> {
                    navController.navigateUp()
               }
            }
        }
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditFlashCardEvent.SaveFlashCard)
            },
            backgroundColor = DarkBlue
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save flashcard"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .background(DarkBlue)
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()

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

                Text(
                    text = "Add flashcards",
                    style = MaterialTheme.typography.h6,
                    color = White,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = {
                            viewModel.onEvent(AddEditFlashCardEvent.SaveFlashCard)
                    },
                    modifier = Modifier.width(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done",
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ){
                AddFlashCardItem(
                    questionState = questionState,
                    answerState = answerState
                )
            }
        }
    }
}