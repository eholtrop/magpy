package com.dpal.rawg.models

data class SearchResponse(
    val count: Int,
    val results: List<GameResponse>
)