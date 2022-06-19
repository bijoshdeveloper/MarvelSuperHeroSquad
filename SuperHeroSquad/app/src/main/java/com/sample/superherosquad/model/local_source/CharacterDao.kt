package com.sample.superherosquad.model.local_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data access object for character table.
 **/
@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterEntity: CharacterEntity)

    @Query("SELECT * FROM characterentity")
    fun getAllCharacters():kotlinx.coroutines.flow.Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    fun getCharacterById(characterId:Int) : kotlinx.coroutines.flow.Flow<CharacterEntity>

    @Query("DELETE FROM characterentity WHERE id = :characterId")
    suspend fun deleteCharacterById(characterId: Int)
}