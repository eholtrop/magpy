package com.dpal.rawg

import com.dpal.rawg.models.GameDetailsResponse
import com.dpal.rawg.models.SearchResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgRetrofitService {

    @GET("games")
    fun search(
        @Query("search")
        query: String,
        @Query("page")
        page: Int,
        @Query("page_size")
        pageSize: Int
    ): Observable<SearchResponse>

    @GET("games/{id}")
    fun details(
        @Path("id")
        id: String
    ): Observable<GameDetailsResponse>
}
