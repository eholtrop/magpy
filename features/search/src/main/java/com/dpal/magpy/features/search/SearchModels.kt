package com.dpal.magpy.features.search

import com.dpal.domain.game.GameTile

sealed class SearchModels {
    class Tiles(val gameTiles: List<GameTile>) : SearchModels()
    sealed class Event : SearchModels() {
        data class GameClicked(
            val gameId: String
        ) : Event()
    }
}
