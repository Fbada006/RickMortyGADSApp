package com.gads.rickmortygadsapp.repo

import androidx.paging.PagingData
import com.gads.rickmortygadsapp.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}