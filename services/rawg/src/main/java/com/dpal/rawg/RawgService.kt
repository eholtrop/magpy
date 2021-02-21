package com.dpal.rawg

import com.dpal.games.data.Game
import com.dpal.games.data.GameService
import com.dpal.games.data.SearchRequest
import com.dpal.libs.optional.Optional
import com.dpal.libs.optional.error
import com.dpal.libs.optional.optional
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
            page = 1,
            pageSize = 30
        )
            .map { toData(it).optional() }
            .onErrorResumeNext {
                    throwable: Throwable ->
                Observable.just(throwable.error())
            }
    }

}

fun toData(response: SearchResponse): List<Game> {
    return response.results.map {
        Game(
            id = it.id,
            name = it.name,
            boxart = it.backgroundImage ?: "",
            releaseDate = it.released
        )
    }
}