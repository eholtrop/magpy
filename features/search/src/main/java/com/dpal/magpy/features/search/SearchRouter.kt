package com.dpal.magpy.features.search

interface SearchRouter {
    fun route(
        event: SearchEvent
    )
}