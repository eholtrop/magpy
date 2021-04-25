package com.dpal.games.data

import java.util.Date

data class Game(
    val id: String,
    val boxart: String,
    val name: String,
    val releaseDate: Date?,
)
