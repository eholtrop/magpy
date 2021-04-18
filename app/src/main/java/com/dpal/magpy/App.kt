package com.dpal.magpy

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import com.dpal.magpy.di.Injector
import com.dpal.magpy.features.details.GameDetailsView
import com.dpal.magpy.features.search.SearchModels
import com.dpal.magpy.features.search.SearchView
import com.dpal.magpy.features.search.exhaustive

sealed class AppState {
    object Search : AppState()
    data class Details(val id: String) : AppState()
}

@Composable
fun App(): (() -> Boolean) {
    var state by remember { mutableStateOf<AppState>(AppState.Search) }

    Box {
        when (val curState = state) {
            AppState.Search ->
                SearchView(
                    Injector.ViewModels.search
                ) { event ->
                    state = when (event) {
                        is SearchModels.Event.GameClicked -> AppState.Details(event.gameId)
                    }
                }
            is AppState.Details -> GameDetailsView(
                Injector.ViewModels.gameDetails(curState.id)
            )()
        }.exhaustive
    }

    return {
        when (state) {
            is AppState.Details -> {
                state = AppState.Search
                true
            }
            else -> false
        }
    }
}
