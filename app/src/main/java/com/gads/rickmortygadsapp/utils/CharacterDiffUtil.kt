package com.gads.rickmortygadsapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.gads.rickmortygadsapp.data.model.Character

object CharacterDiffUtil : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }
}
