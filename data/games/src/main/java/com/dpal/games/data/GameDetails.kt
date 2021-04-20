package com.dpal.games.data

import java.util.Date

data class GameDetails(
    val id: String,
    val boxart: String,
    val name: String,
    val releaseDate: Date?,
    val description: String,
)
