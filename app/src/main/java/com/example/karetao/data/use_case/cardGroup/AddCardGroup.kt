package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepository
import com.example.karetao.model.CardGroup
import com.example.karetao.model.FlashCard
import com.example.karetao.model.InvalidCardGroupException
import com.example.karetao.model.InvalidFlashCardException

class AddCardGroup(
    private val repository: CardGroupRepository
) {
    @Throws(InvalidCardGroupException::class)
    suspend operator fun invoke(cardGroup: CardGroup){
        if(cardGroup.groupName.isBlank()){
            throw InvalidCardGroupException("The name of the Card Group can't be empty.")
        }
        repository.insertCardGroup(cardGroup)
    }
}