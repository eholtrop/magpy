package com.dpal.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dpal.domain.game.Game
import com.dpal.search.databinding.AdapterGameBinding

class GameViewHolder(
    private val binding: AdapterGameBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Game) {
        binding.boxart.load(model.boxart)
    }
}

class GameAdapter: ListAdapter<Game, GameViewHolder>(
    GameDiffer()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(AdapterGameBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}

class GameDiffer: DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}