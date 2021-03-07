package com.dpal.domain.details

import com.dpal.games.data.GameDetails
import com.dpal.games.data.GameRepository
import com.example.dates.formatForUi
import io.reactivex.rxjava3.core.Observable

class GetGameDetailsUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(id: String): Observable<GameDomain> {
        return gameRepository.details(id).map { it.toDomain() }
    }
}

fun GameDetails.toDomain(): GameDomain {
    return GameDomain(
        title = this.name,
        subtitle = this.releaseDate.formatForUi(),
        boxArt = this.boxart,
        description = this.description
    )
}

data class GameDomain(
    val title: String,
    val subtitle: String,
    val boxArt: String,
    val description: String
)
