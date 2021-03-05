package com.dpal.domain.search

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

data class SearchTile(
    val id: String,
    val name: String,
    val boxArt: String,
    val releaseDate: String,
) {
    private val clickSubject = PublishSubject.create<SearchTile>()
    val clicked: Observable<SearchTile> = clickSubject.hide()

    fun click() = clickSubject.onNext(this)
}
