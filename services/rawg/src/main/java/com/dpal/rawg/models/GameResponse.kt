package com.dpal.rawg.models

import com.google.gson.annotations.SerializedName

data class GameResponse(
    val id: String,
    val name: String,
    @SerializedName("background_image")
    val backgroundImage: String?
)