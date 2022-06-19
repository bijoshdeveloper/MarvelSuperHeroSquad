package com.sample.superherosquad.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.superherosquad.model.local_source.CharacterEntity
import com.sample.superherosquad.model.remote_source.api.ApiResult
import com.sample.superherosquad.model.repository.DetailRepository
import com.sample.superherosquad.ui.view_detail.DetailState
import com.sample.superherosquad.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel class for detail screen.
 **/
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val characterId = savedStateHandle.get<Int>(Constants.CHARACTER_ID)

    private val _uiDetailState = mutableStateOf(DetailState())
    val uiDetailState: State<DetailState> = _uiDetailState

    init {
        characterId?.let { getCharacterById(characterId = it) }
    }

    val characterFromDb = characterId?.let { detailRepository.getCharacterFromDb(characterId = it) }

    private fun getCharacterById(characterId:Int){
        viewModelScope.launch (Dispatchers.IO){
            withContext(Dispatchers.Main){
                _uiDetailState.value = DetailState(isLoading = true)
            }
            when (val result = detailRepository.getCharacterById(characterId = characterId)) {
                is ApiResult.Success -> {
                    _uiDetailState.value = DetailState(
                        isSuccess = true,
                        data = result.data
                    )
                }

                is ApiResult.Error -> {
                    _uiDetailState.value = DetailState(
                        isError = true,
                        errorMessage = result.errorMessage
                    )
                }
            }
        }
    }

    fun insertCharacter(characterEntity: CharacterEntity){
        viewModelScope.launch {
            detailRepository.insertCharacterToDb(characterEntity)
        }
    }

    fun deleteCharacter(characterId: Int){
        viewModelScope.launch {
            detailRepository.deleteCharacterFromDb(characterId = characterId)
        }
    }
}