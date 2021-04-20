package com.dpal.magpy

sealed class AppState {
    object Search : AppState()
    data class Details(val id: String) : AppState()
}
