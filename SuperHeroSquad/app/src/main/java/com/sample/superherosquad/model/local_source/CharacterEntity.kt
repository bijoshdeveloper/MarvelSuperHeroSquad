package com.sample.superherosquad.model.local_source

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Character room database table.
 **/
@Entity
data class CharacterEntity(
    @PrimaryKey val id: Int? = null,
    val characterName: String = "",
    val characterThumbnail: String = ""
)