package com.dpal.rawg.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class GameResponse(
    val id: String,
    val name: String,
    @SerializedName("background_image")
    val backgroundImage: String?,
    val released: Date?
)