package com.dpal.magpy.features.search

import com.dpal.domain.search.SearchTile

sealed class SearchModels {
    class Tiles(val gameTiles: List<SearchTile>) : SearchModels()
    sealed class Event : SearchModels() {
        data class GameClicked(
            val gameId: String
        ) : Event()
    }
}
