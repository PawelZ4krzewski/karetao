package com.example.karetao.presentation.cardgroups

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karetao.data.use_case.OrderType
import com.example.karetao.data.use_case.cardGroup.CardGroupOrderType
import com.example.karetao.data.use_case.cardGroup.CardGroupUseCases
import com.example.karetao.data.use_case.flashCard.FlashCardUseCases
import com.example.karetao.model.CardGroup
import com.example.karetao.model.CardGroupInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardGroupViewModel @Inject constructor(
    private val cardGroupUseCases: CardGroupUseCases,
    private val flashCardUseCases: FlashCardUseCases
): ViewModel(){

    private val _state = mutableStateOf(CardGroupState())
    val state: State<CardGroupState> = _state


    private var recentlyDeletedCardGroup: CardGroup? = null

    private var getCardGroupsJob: Job? = null

    init{
        getCardGroups(CardGroupOrderType.GroupName(OrderType.Ascending))
    }

    fun onEvent(event: CardGroupEvent){
        when(event){
            is CardGroupEvent.Order -> {
                if(state.value.cardGroupOrder::class == event.cardGroupOrder::class &&
                    state.value.cardGroupOrder.orderType == event.cardGroupOrder.orderType
                ){
                    return
                }
                getCardGroups(event.cardGroupOrder)
            }
            is CardGroupEvent.DeleteCardGroup -> {
                viewModelScope.launch {
                    cardGroupUseCases.deleteCardGroup(event.cardGroup)
                    recentlyDeletedCardGroup = event.cardGroup
                }
            }

            is CardGroupEvent.RestoreCardGroups -> {
                viewModelScope.launch{
                    cardGroupUseCases.addCardGroup(recentlyDeletedCardGroup?: return@launch)
                    recentlyDeletedCardGroup = null
                }
            }

            is CardGroupEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getCardGroups(cardGroupOrder: CardGroupOrderType){
        Log.d("CardGroups-ViewModel", "GetCardGroups")

        var flashCardAmount: Int
        getCardGroupsJob?.cancel()
        getCardGroupsJob = cardGroupUseCases.getCardGroups(cardGroupOrder)
            .onEach { cardGroup ->
                val cardGroupInformation  = mutableListOf<CardGroupInformation>()
                cardGroup.onEach {
                    flashCardAmount = flashCardUseCases.getFlashCardAmount(it.groupId!!)
                    cardGroupInformation.add(CardGroupInformation(cardGroup = it, flashCardAmount))
                }

                _state.value = state.value.copy(
                    cardGroupsInformation = cardGroupInformation,
                    cardGroupOrder = cardGroupOrder
                )

                Log.d("CardGroups-ViewModel", "Card group Size " + state.value.cardGroupsInformation.size.toString())
            }
           .launchIn(viewModelScope)

    }
}