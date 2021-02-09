package com.dpal.domain.game

import com.dpal.games.Game
import com.dpal.games.GameRepository
import com.dpal.games.SearchRequest
import io.reactivex.rxjava3.core.Observable

class SearchForGamesUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(
        query: String,
        page: Int
    ): Observable<Search> {
        return gameRepository.search(
            SearchRequest(
                query = query
            )
        )
            .map { toDomain(it) }
    }

    companion object {

        fun toDomain(data: List<Game>): Search {
            return Search.Data(
                games = data.map {
                    GameTile(
                        name = it.name,
                        boxArt = it.boxart
                    )
                }
            )
        }

    }
}

sealed class Search {
    data class Data(
        val games: List<GameTile>
    ): Search()
}

data class GameTile(
    val name: String,
    val boxArt: String
)