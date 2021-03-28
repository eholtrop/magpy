package com.dpal.magpy.features.search

import com.avianapps.drivable.Drivable
import com.dpal.domain.search.SearchForGamesUseCase
import com.dpal.domain.search.SearchTile
import com.dpal.libs.rxcache.ObservableCache
import com.jakewharton.rx3.replayingShare
import hu.akarnokd.rxjava3.bridge.RxJavaBridge.toV3Observable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(
    val searchForGamesUseCase: SearchForGamesUseCase
) {

    val input = Input()

    class Input {
        val query = Drivable<String>()
        val loadMore = Drivable<Unit>()
    }

    private val queryCache = ObservableCache(toV3Observable(input.query))
    private val pageCache = ObservableCache(
        toV3Observable(input.loadMore)
            .scan(1) { page, _ -> page + 1 }
    )

    private val searchActiveSubject = PublishSubject.create<Boolean>()
    val searchActive: Observable<Boolean> = searchActiveSubject.hide()

    val games: Observable<List<SearchTile>> = queryCache
        .debounce(300, TimeUnit.MILLISECONDS)
        .switchMap { query ->
            pageCache.clear()
            pageCache
                .concatMap { page ->
                    searchForGamesUseCase(
                        query,
                        page
                    ).doOnLifecycle(
                        { searchActiveSubject.onNext(true) },
                        { searchActiveSubject.onNext(false) }
                    )
                        .doOnNext { searchActiveSubject.onNext(false) }
                }
                .scan(emptyList<SearchTile>()) { list, page -> list + page }
        }
        .replayingShare()

    val gameClicked = games.map { it.map { it.clicked } }
        .flatMapIterable { it }
        .flatMap { it }
        .map { SearchModels.Event.GameClicked(it.id) }
}
