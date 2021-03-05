package com.dpal.games.data

import com.dpal.libs.optional.Optional
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

data class SearchRequest(
    val query: String,
    val page: Int,
    val pageSize: Int
)

interface GameService {
    fun search(request: SearchRequest): Observable<Optional<List<Game>>>
}

interface GameRepository {
    fun search(request: SearchRequest): Observable<List<Game>>
}

class GameRepositoryImpl(
    val gameService: GameService
) : GameRepository {
    override fun search(request: SearchRequest): Observable<List<Game>> {
        return gameService.search(request)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    is Optional.Value<List<Game>> -> it.value
                    is Optional.Error -> emptyList()
                }
            }
    }
}
