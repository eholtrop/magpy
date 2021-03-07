package com.dpal.magpy.features.details

import com.dpal.domain.details.GameDomain
import com.dpal.domain.details.GetGameDetailsUseCase
import io.reactivex.rxjava3.core.Observable

class GameDetailsViewModel(
    private val id: String,
    private val gameDetailsUseCase: GetGameDetailsUseCase
) {

    fun details(id: String = this.id): Observable<ViewState> = gameDetailsUseCase(id).map { ViewState.Loaded(it) }
}

sealed class ViewState {
    object Loading : ViewState()
    data class Loaded(val data: GameDomain) : ViewState()
}
