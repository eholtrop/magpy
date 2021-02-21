package com.dpal.games.data

import com.dpal.libs.optional.Optional
import io.reactivex.rxjava3.core.Observable


data class SearchRequest(
    val query: String
)

interface GameService {
    fun search(request: SearchRequest): Observable<Optional<List<Game>>>
}

interface GameRepository {
    fun search(request: SearchRequest): Observable<List<Game>>
}

class GameRepositoryImpl(
    val gameService: GameService
): GameRepository {
    override fun search(request: SearchRequest): Observable<List<Game>> {
        return gameService.search(request)
            .map {
                when (it) {
                    is Optional.Value<List<Game>> -> it.value
                    is Optional.Error -> emptyList()
                }
            }
    }
}