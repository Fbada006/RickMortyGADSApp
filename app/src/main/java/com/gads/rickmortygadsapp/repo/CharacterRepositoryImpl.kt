package com.gads.rickmortygadsapp.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gads.rickmortygadsapp.data.RickMortyDataSource
import com.gads.rickmortygadsapp.data.model.Character
import com.gads.rickmortygadsapp.network.RickMortyApiService
import com.gads.rickmortygadsapp.utils.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val apiService: RickMortyApiService
) : CharacterRepository {

    private val pagingConfig = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
    )

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { RickMortyDataSource(apiService) }
        ).flow
    }
}