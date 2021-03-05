package com.dpal.magpy.features.search

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dpal.domain.search.SearchTile
import com.dpal.search.databinding.AdapterGameBinding

class GameViewHolder(
    private val binding: AdapterGameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: SearchTile) {
        binding.image.load(model.boxArt)
        binding.title.text = model.name
        binding.releaseDate.text = model.releaseDate
        binding.root.setOnClickListener { model.click() }
    }
}
