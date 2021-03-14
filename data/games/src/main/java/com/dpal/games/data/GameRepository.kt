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
    fun details(id: String): Observable<Optional<GameDetails>>
}

interface GameRepository {
    fun search(request: SearchRequest): Observable<List<Game>>
    fun details(id: String): Observable<GameDetails>
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

    override fun details(id: String): Observable<GameDetails> {
        return gameService.details(id)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    is Optional.Error -> throw it.throwable
                    is Optional.Value -> it.value
                }
            }
    }
}
