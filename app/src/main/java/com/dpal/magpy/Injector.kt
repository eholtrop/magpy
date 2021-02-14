package com.dpal.magpy

import com.dpal.domain.game.SearchForGamesUseCase
import com.dpal.games.GameRepository
import com.dpal.games.GameRepositoryImpl
import com.dpal.games.GameService
import com.dpal.rawg.RawgRetrofitService
import com.dpal.rawg.RawgService
import com.dpal.search.SearchViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val gson: Gson by lazy {
        GsonBuilder().setDateFormat("yyyy-mm-dd").create()
    }

    val gameService: GameService get() {
        return RawgService(retrofit)
    }

    val gameRepository: GameRepository get() {
        return GameRepositoryImpl(
            gameService
        )
    }

    val searchForGamesUseCase: SearchForGamesUseCase get() {
        return SearchForGamesUseCase(
            gameRepository
        )
    }

    val searchViewModel: SearchViewModel get() {
        return SearchViewModel(
            searchForGamesUseCase
        )
    }

}