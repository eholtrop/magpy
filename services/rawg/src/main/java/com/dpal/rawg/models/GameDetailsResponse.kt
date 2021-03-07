package com.dpal.rawg.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class GameDetailsResponse(
    val id: String,
    val name: String,
    val description: String,
    val released: Date?,
    @SerializedName("background_image")
    val backgroundImage: String?,
)