package com.sample.superherosquad.ui.home

import com.sample.superherosquad.model.remote_source.model.CharactersResponse

/**
 * Data class to manage home screen ui state.
 **/
data class HomeState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val data: CharactersResponse? = null,
    val errorMessage: String = ""
)