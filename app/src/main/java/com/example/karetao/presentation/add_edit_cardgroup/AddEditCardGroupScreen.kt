package com.example.karetao.presentation.add_edit_cardgroup


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
import com.example.karetao.presentation.add_edit_flashcard.components.TransparentHintTextField
import com.example.karetao.ui.theme.DarkBlue
import com.example.karetao.ui.theme.White
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
                backgroundColor = DarkBlue
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save cardgroup"
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
//                        navController.popBackStack()
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
                    text = "Create flashcard group",
                    style = MaterialTheme.typography.h6,
                    color = White,
                    fontWeight = FontWeight.Bold


                )

                IconButton(
                    onClick = {
//                        viewModel.onEvent(AddEditCardGroupEvent.SaveCardGroup)
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
                Box(
                    modifier = Modifier
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .background(White, RoundedCornerShape(10.dp))
                            .padding(30.dp)
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
                            singleLine = true,
                            textStyle = MaterialTheme.typography.h6
                        )
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
                            text = "TITLE",
                            style = MaterialTheme.typography.body1
                        )

                        Spacer(modifier = Modifier.height(20.dp))

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
                            singleLine = true,
                            textStyle = MaterialTheme.typography.h6
                        )

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
                            text = "DESCRIPTION",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }

        }
    }

}