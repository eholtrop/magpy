package com.dpal.search

import com.avianapps.drivable.Drivable
import com.dpal.domain.game.Search
import com.dpal.domain.game.SearchForGamesUseCase
import hu.akarnokd.rxjava3.bridge.RxJavaBridge.toV3Observable
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class SearchViewModel(
    val searchForGamesUseCase: SearchForGamesUseCase
) {

    val input = Input()

    class Input {
        val query = Drivable<String>()
    }

    val games: Observable<Search> = toV3Observable(input.query)
        .debounce(300, TimeUnit.MILLISECONDS)
        .switchMap {
            searchForGamesUseCase(
                it,
                0
            )
        }
}
