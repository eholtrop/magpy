package com.dpal.rawg

import com.dpal.games.data.Game
import com.dpal.games.data.GameDetails
import com.dpal.games.data.GameService
import com.dpal.games.data.SearchRequest
import com.dpal.libs.optional.Optional
import com.dpal.libs.optional.optional
import com.dpal.rawg.models.GameDetailsResponse
import com.dpal.rawg.models.SearchResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit

class RawgService(
    val retrofit: Retrofit
) : GameService {

    private val service = retrofit.create(RawgRetrofitService::class.java)

    override fun search(request: SearchRequest): Observable<Optional<List<Game>>> {
        return service.search(
            query = request.query,
            page = request.page,
            pageSize = request.pageSize
        )
            .map { it.toGames().optional() }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.just(Optional.Error(throwable))
            }
    }

    override fun details(id: String): Observable<Optional<GameDetails>> {
        return service.details(id)
            .map {
                it.toGameDetails().optional()
            }
            .onErrorResumeNext { throwable: Throwable ->
                Observable.just(Optional.Error(throwable))
            }
    }
}

fun SearchResponse.toGames(): List<Game> {
    return this.results.map {
        Game(
            id = it.id,
            name = it.name,
            boxart = it.backgroundImage ?: "",
            releaseDate = it.released
        )
    }
}

fun GameDetailsResponse.toGameDetails(): GameDetails {
    return GameDetails(
        id = this.id,
        name = this.name,
        description = this.description,
        releaseDate = this.released,
        boxart = this.backgroundImage ?: ""
    )
}
