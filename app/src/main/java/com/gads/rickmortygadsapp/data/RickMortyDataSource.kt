package com.gads.rickmortygadsapp.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gads.rickmortygadsapp.data.model.Character
import com.gads.rickmortygadsapp.data.model.Info
import com.gads.rickmortygadsapp.data.model.RickMortyResponse
import com.gads.rickmortygadsapp.network.RickMortyApiService
import com.gads.rickmortygadsapp.utils.STARTING_PAGE

class RickMortyDataSource(
    private val apiService: RickMortyApiService
) : PagingSource<Int, Character>() {

    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = if (params.key == null) STARTING_PAGE else params.key

        try {
            val response: RickMortyResponse = apiService.getAllCharacters()
            val info: Info = response.info
            val next: String? = Uri.parse(info.next).getQueryParameter("page")

            val characters: List<Character> = response.results

            return LoadResult.Page(
                data = characters,
                prevKey = if (position == STARTING_PAGE) null else position!! - 1,
                nextKey = next?.toInt()
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}