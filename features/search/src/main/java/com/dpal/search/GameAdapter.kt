package com.dpal.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dpal.domain.game.GameTile
import com.dpal.search.databinding.AdapterGameBinding

class GameViewHolder(
    private val binding: AdapterGameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: GameTile) {
        binding.image.load(model.boxArt)
        binding.title.text = model.name
        binding.releaseDate.text = model.releaseDate
    }
}

class GameAdapter : ListAdapter<GameTile, GameViewHolder>(
    GameDiffer()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(AdapterGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class GameDiffer : DiffUtil.ItemCallback<GameTile>() {
    override fun areItemsTheSame(oldItem: GameTile, newItem: GameTile): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: GameTile, newItem: GameTile): Boolean {
        return oldItem == newItem
    }
}
