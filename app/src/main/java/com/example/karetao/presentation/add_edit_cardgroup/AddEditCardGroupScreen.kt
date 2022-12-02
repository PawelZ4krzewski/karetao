package com.example.karetao.presentation.add_edit_cardgroup


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
fun AddEditCardGroupScreen(
    navController: NavController,
    viewModel: AddEditCardGroupViewModel = hiltViewModel()
) {
    val groupNameState = viewModel.cardGroupName.value
    val descriptionState = viewModel.cardGroupDescription.value

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AddEditCardGroupViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditCardGroupViewModel.UiEvent.SaveCardGroup -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditCardGroupEvent.SaveCardGroup)
            },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save cardgroup"
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
                text = groupNameState.text,
                hint = groupNameState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditCardGroupEvent.EnteredGroupName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditCardGroupEvent.ChangeGroupNameFocus(it))
                },
                isHintVisible = groupNameState.isHintVisible,
                singleLine =  true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer( modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = descriptionState.text,
                hint = descriptionState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditCardGroupEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditCardGroupEvent.ChangeDescriptionFocus(it))
                },
                isHintVisible = descriptionState.isHintVisible,
                singleLine =  true,
                textStyle = MaterialTheme.typography.h5
            )
        }
    }

}