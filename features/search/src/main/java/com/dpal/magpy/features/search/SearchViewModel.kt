package com.dpal.magpy.features.search

import com.avianapps.drivable.Drivable
import com.dpal.domain.game.GameTile
import com.dpal.domain.game.SearchForGamesUseCase
import com.jakewharton.rx3.replayingShare
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

    val games: Observable<List<GameTile>> = toV3Observable(input.query)
        .debounce(300, TimeUnit.MILLISECONDS)
        .switchMap {
            searchForGamesUseCase(
                it,
                0
            )
        }
        .replayingShare()

    val gameClicked = games
        .map { it.map { it.clicked } }
        .flatMapIterable { it }
        .flatMap { it }
        .map { SearchEvent.GameClicked(it.boxArt) }
}
