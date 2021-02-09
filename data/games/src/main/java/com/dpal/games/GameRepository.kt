package com.dpal.games

import io.reactivex.rxjava3.core.Observable


data class SearchRequest(
    val query: String
)

interface GameService {
    fun search(request: SearchRequest): Observable<List<Game>>
}

interface GameRepository {
    fun search(request: SearchRequest): Observable<List<Game>>
}

class GameRepositoryImpl(
    val gameService: GameService
): GameRepository {
    override fun search(request: SearchRequest): Observable<List<Game>> {
        return gameService.search(request)
    }
}