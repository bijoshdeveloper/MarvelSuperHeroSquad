package com.sample.superherosquad.model.repository

import com.sample.superherosquad.model.local_source.CharacterDao
import com.sample.superherosquad.model.local_source.CharacterEntity
import com.sample.superherosquad.model.remote_source.api.ApiResult
import com.sample.superherosquad.model.remote_source.api.ApiService
import com.sample.superherosquad.model.remote_source.model.CharactersResponse
import com.sample.superherosquad.util.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository class for Detail screen.
 **/
class DetailRepository @Inject constructor(
    private val apiService: ApiService,
    private val characterDao: CharacterDao
) {
    suspend fun getCharacterById(characterId : Int) : ApiResult<CharactersResponse?> {
        return try {
            val result = apiService.getCharacterById(characterId = characterId)
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

    suspend fun insertCharacterToDb(characterEntity: CharacterEntity){
        characterDao.insertCharacter(characterEntity)
    }

    suspend fun deleteCharacterFromDb(characterId: Int){
        characterDao.deleteCharacterById(characterId = characterId)
    }

    fun getCharacterFromDb(characterId: Int) : Flow<CharacterEntity>{
        return characterDao.getCharacterById(characterId = characterId)
    }
}