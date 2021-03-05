package com.dpal.magpy.features.search

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
        binding.root.setOnClickListener { model.click() }
    }
}
