package com.dpal.magpy

import com.dpal.domain.game.SearchForGamesUseCase
import com.dpal.games.data.GameRepository
import com.dpal.games.data.GameRepositoryImpl
import com.dpal.games.data.GameService
import com.dpal.magpy.features.details.DetailsFragment
import com.dpal.magpy.features.search.SearchEvent
import com.dpal.magpy.features.search.SearchFragment
import com.dpal.magpy.features.search.SearchRouter
import com.dpal.rawg.RawgService
import com.dpal.magpy.features.search.SearchViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    lateinit var appCoordinator: AppCoordinator

    fun init(
        activity: MainActivity
    ) {
        appCoordinator = AppCoordinator(activity.supportFragmentManager)
    }


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

    val gameService: GameService by lazy {
        RawgService(retrofit)
    }

    val gameRepository: GameRepository
        get() {
            return GameRepositoryImpl(
                gameService
            )
        }

    val searchForGamesUseCase: SearchForGamesUseCase
        get() {
            return SearchForGamesUseCase(
                gameRepository
            )
        }

    val searchViewModel: SearchViewModel
        get() {
            return SearchViewModel(
                searchForGamesUseCase
            )
        }

    val searchRouter: SearchRouter
        get() {
            return object : SearchRouter {
                override fun route(event: SearchEvent) {
                    when (event) {
                        is SearchEvent.GameClicked -> appCoordinator.state =
                            AppState.Details(event.gameId)
                    }
                }
            }
        }

    val searchFragment: SearchFragment
        get() {
            return SearchFragment(
                searchViewModel,
                searchRouter
            )
        }

    fun detailsFragment(
        imageUrl: String
    ): DetailsFragment {
        return DetailsFragment(
            "",
            imageUrl
        )
    }

}