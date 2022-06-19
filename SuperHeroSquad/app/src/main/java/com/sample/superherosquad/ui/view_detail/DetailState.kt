package com.sample.superherosquad.ui.view_detail

import com.sample.superherosquad.model.local_source.CharacterEntity
import com.sample.superherosquad.model.remote_source.model.CharactersResponse
import com.sample.superherosquad.model.remote_source.model.ResultsItem

/**
 * Data class to handle detail screen state.
 **/
data class DetailState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val data: CharactersResponse? = null,
    val errorMessage: String = ""
)

/**
 * Extension function for converting ResultItem to CharacterEntity.
 **/
fun ResultsItem.toCharacterEntity() : CharacterEntity{
    return CharacterEntity(
        id = id,
        characterName = name!!,
        characterThumbnail = "${thumbnail?.path}.${thumbnail?.extension}"
    )
}