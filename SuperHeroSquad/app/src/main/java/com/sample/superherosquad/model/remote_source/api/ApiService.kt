package com.sample.superherosquad.model.remote_source.api

import com.sample.superherosquad.model.remote_source.model.CharactersResponse
import com.sample.superherosquad.util.Constants.GET_CHARACTERS
import com.sample.superherosquad.util.Constants.GET_CHARACTERS_BY_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ApiService interface for Retrofit API calls.
 **/
interface ApiService {
    @GET(GET_CHARACTERS)
    suspend fun getCharacters(
        @Query("limit") limit: Int,
    ) : Response<CharactersResponse>

    @GET(GET_CHARACTERS_BY_ID)
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int
    ) : Response<CharactersResponse>
}