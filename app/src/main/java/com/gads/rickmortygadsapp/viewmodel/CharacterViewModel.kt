package com.gads.rickmortygadsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gads.rickmortygadsapp.data.model.Character
import com.gads.rickmortygadsapp.repo.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val mutableCharacterData: MutableStateFlow<PagingData<Character>?> =
        MutableStateFlow(null)
    val characterData = mutableCharacterData.asStateFlow()

    fun getCharacters() {
        viewModelScope.launch {
            repository.getCharacters()
                .cachedIn(viewModelScope)
                .collect {data ->
                    mutableCharacterData.value = data
                }
        }
    }
}