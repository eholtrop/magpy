package com.dpal.magpy.features.details

import android.nfc.Tag
import com.dpal.domain.details.GameDomain
import com.dpal.domain.details.GetGameDetailsUseCase
import com.dpal.domain.details.RemoveGameTagUseCase
import com.dpal.domain.details.SaveGameTagUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

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
