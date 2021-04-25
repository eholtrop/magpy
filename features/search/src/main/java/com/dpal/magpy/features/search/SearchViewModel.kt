package com.dpal.magpy.features.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dpal.domain.search.SearchForGamesUseCase
import com.dpal.domain.search.SearchTile
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(
    val searchForGamesUseCase: SearchForGamesUseCase
) : ViewModel() {

    val searchSubject = BehaviorSubject.createDefault("")
    val loadMoreSubject = BehaviorSubject.createDefault("")

    val output = Output(this)

    class Output(val viewModel: SearchViewModel) {

        private var gamesCache = emptyList<SearchTile>()

        @Composable
        fun games() = viewModel.games.doOnNext { gamesCache = it }.subscribeAsState(initial = gamesCache)
    }

    private val searchActiveSubject = PublishSubject.create<Boolean>()
    val searchActive: Observable<Boolean> = searchActiveSubject.hide()

    private val games: Observable<List<SearchTile>> = searchSubject
        .debounce(300, TimeUnit.MILLISECONDS)
        .switchMap { query ->
            loadMoreSubject
                .scan(0) { currentPage, _ -> currentPage + 1 }
                .concatMap { page ->
                    searchForGamesUseCase(
                        query = query,
                        page = page
                    ).doOnLifecycle(
                        { searchActiveSubject.onNext(true) },
                        { searchActiveSubject.onNext(false) }
                    )
                        .doOnNext { searchActiveSubject.onNext(false) }
                }
                .scan(emptyList<SearchTile>()) { list, page -> list + page }
        }
}

val <T : Any> T.exhaustive: T get() = this
