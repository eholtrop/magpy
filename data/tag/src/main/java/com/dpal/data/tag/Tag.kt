package com.dpal.data.tag

data class Tag(
    val id: String,
    val title: String,
    internal var resources: MutableList<String>
) {
    fun resources(): List<String> = resources.toList()
}
