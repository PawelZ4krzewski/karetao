package com.example.karetao.data.use_case.cardGroup

import com.example.karetao.data.repository.CardGroupRepository
import com.example.karetao.model.CardGroup
import com.example.karetao.model.InvalidCardGroupException
import javax.inject.Inject

class AddCardGroupUseCase @Inject constructor(
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