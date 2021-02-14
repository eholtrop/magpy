package com.dpal.magpy.features.search

sealed class SearchEvent {

    data class GameClicked(
        val gameId: String
    ): SearchEvent()
}