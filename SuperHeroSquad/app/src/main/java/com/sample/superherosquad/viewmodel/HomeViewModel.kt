package com.sample.superherosquad.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.superherosquad.model.remote_source.api.ApiResult
import com.sample.superherosquad.model.repository.HomeRepository
import com.sample.superherosquad.ui.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel class for home screen.
 **/
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiHomeState = mutableStateOf(HomeState())
    val uiHomeState: State<HomeState> = _uiHomeState

    init {
        getAllCharacters()
    }

    fun getAllCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                _uiHomeState.value = HomeState(isLoading = true)
            }
            when (val result = homeRepository.getAllCharacters()) {
                is ApiResult.Success -> {
                    _uiHomeState.value = HomeState(
                        isSuccess = true,
                        data = result.data
                    )
                }

                is ApiResult.Error -> {
                    _uiHomeState.value = HomeState(
                        isError = true,
                        errorMessage = result.errorMessage
                    )
                }
            }
        }
    }

    val squadFromDb = homeRepository.getSquadFromDb()
}