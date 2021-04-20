package com.dpal.magpy.features.details

import com.dpal.domain.details.GameDomain
import com.dpal.domain.details.GetGameDetailsUseCase
import com.dpal.domain.details.RemoveGameTagUseCase
import com.dpal.domain.details.SaveGameTagUseCase
import io.reactivex.rxjava3.core.Observable

class GameDetailsViewModel(
    private val id: String,
    private val gameDetailsUseCase: GetGameDetailsUseCase,
    private val saveGameTagUseCase: SaveGameTagUseCase,
    private val removeGameTagUseCase: RemoveGameTagUseCase
) {

    fun details(): Observable<ViewState> = gameDetailsUseCase(id).map { ViewState.Loaded(it) }

    fun addTag(tagId: String) = saveGameTagUseCase(
        tagId = tagId,
        gameId = id
    )

    fun removeTag(tagId: String) = removeGameTagUseCase(tagId = tagId, gameId = id)
}

sealed class ViewState {
    object Loading : ViewState()
    data class Loaded(val data: GameDomain) : ViewState()
}
