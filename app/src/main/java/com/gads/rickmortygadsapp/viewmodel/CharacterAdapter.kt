package com.gads.rickmortygadsapp.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gads.rickmortygadsapp.data.model.Character
import com.gads.rickmortygadsapp.databinding.ItemCharacterBinding
import com.gads.rickmortygadsapp.utils.CharacterDiffUtil

class CharacterAdapter :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(CharacterDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context))
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character: Character? = getItem(position)
        if (character != null) {
            holder.bind(character)
        }
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.tvName.text = character.name
            binding.tvOrigin.text = character.origin.name
        }
    }
}