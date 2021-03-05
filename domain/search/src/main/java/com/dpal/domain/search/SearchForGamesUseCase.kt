package com.dpal.domain.search

import com.dpal.games.data.Game
import com.dpal.games.data.GameRepository
import com.dpal.games.data.SearchRequest
import io.reactivex.rxjava3.core.Observable
import java.text.SimpleDateFormat

typealias Page = Int

private const val PageSize = 10

fun Int.toPage(): Page = this / PageSize

class SearchForGamesUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(
        query: String,
        page: Int
    ): Observable<List<SearchTile>> {
        return gameRepository.search(
            SearchRequest(
                query = query,
                page = page,
                PageSize
            )
        )
            .map { toDomain(it) }
    }

    companion object {

        fun toDomain(data: List<Game>): List<SearchTile> {
            return data.map {
                SearchTile(
                    id = it.id,
                    name = it.name,
                    boxArt = it.boxart,
                    releaseDate = it.releaseDate?.let { SimpleDateFormat("MMMM dd, yyyy").format(it) }
                        ?: "Unreleased"
                )
            }
        }
    }
}
