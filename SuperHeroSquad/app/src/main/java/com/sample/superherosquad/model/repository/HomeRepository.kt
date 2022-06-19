package com.sample.superherosquad.model.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.superherosquad.model.local_source.CharacterDao
import com.sample.superherosquad.model.local_source.CharacterEntity
import com.sample.superherosquad.model.remote_source.api.ApiResult
import com.sample.superherosquad.model.remote_source.api.ApiService
import com.sample.superherosquad.model.remote_source.model.CharactersResponse
import com.sample.superherosquad.model.remote_source.model.ErrorResponse
import com.sample.superherosquad.util.Constants.LIMIT
import com.sample.superherosquad.util.Utils
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject

/**
 * Repository class for Home screen.
 **/
class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val characterDao: CharacterDao
){
    suspend fun getAllCharacters(): ApiResult<CharactersResponse?> {
        return try {
            val result = apiService.getCharacters(LIMIT)
            if(result.code() == 200 && result.isSuccessful){
                ApiResult.Success(data = result.body())
            }else{
                val errorBody = Utils.getErrorBody(result.errorBody()!!)
                ApiResult.Error(errorMessage = errorBody.status!!)
            }
        }catch (e: Exception){
            ApiResult.Error(errorMessage = e.message.toString())
        }
    }

    fun getSquadFromDb() : Flow<List<CharacterEntity>> {
        return characterDao.getAllCharacters()
    }
}