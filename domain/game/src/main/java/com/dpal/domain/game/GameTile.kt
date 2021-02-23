package com.dpal.domain.game

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

data class GameTile(
    val id: String,
    val name: String,
    val boxArt: String,
    val releaseDate: String,
) {
    private val clickSubject = PublishSubject.create<GameTile>()
    val clicked: Observable<GameTile> = clickSubject.hide()

    fun click() = clickSubject.onNext(this)
}
