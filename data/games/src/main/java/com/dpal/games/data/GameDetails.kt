package com.dpal.games.data

import java.util.*

data class GameDetails(
    val id: String,
    val boxart: String,
    val name: String,
    val releaseDate: Date?,
    val description: String,
)
