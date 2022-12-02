package com.example.karetao.presentation.add_edit_flashcard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.karetao.presentation.add_edit_flashcard.components.TransparentHintTextField
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
            backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save flashcard"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TransparentHintTextField(
                text = questionState.text,
                hint = questionState.hint,
                onValueChange = {
                      viewModel.onEvent(AddEditFlashCardEvent.EnteredQuestion(it))          
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.ChangeQuestionFocus(it))
                },
                isHintVisible = questionState.isHintVisible,
                singleLine =  true,
                textStyle = MaterialTheme.typography.h5
            )
            
            Spacer( modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = answerState.text,
                hint = answerState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.EnteredAnswer(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditFlashCardEvent.ChangeAnswerFocus(it))
                },
                isHintVisible = answerState.isHintVisible,
                singleLine =  true,
                textStyle = MaterialTheme.typography.h5
            )
        }
    }

}