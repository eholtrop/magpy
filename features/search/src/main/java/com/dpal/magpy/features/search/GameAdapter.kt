package com.dpal.magpy.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dpal.domain.search.SearchTile
import com.dpal.search.databinding.AdapterGameBinding

class GameAdapter : ListAdapter<SearchTile, GameViewHolder>(
    GameDiffer()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(AdapterGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class GameDiffer : DiffUtil.ItemCallback<SearchTile>() {
    override fun areItemsTheSame(oldItem: SearchTile, newItem: SearchTile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchTile, newItem: SearchTile): Boolean {
        return oldItem == newItem
    }
}
