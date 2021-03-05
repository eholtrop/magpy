package com.dpal.magpy.di

import com.dpal.domain.search.SearchForGamesUseCase
import com.dpal.games.data.GameRepository
import com.dpal.games.data.GameRepositoryImpl
import com.dpal.games.data.GameService
import com.dpal.magpy.AppCoordinator
import com.dpal.magpy.AppState
import com.dpal.magpy.MainActivity
import com.dpal.magpy.features.details.DetailsFragment
import com.dpal.magpy.features.search.SearchFragment
import com.dpal.magpy.features.search.SearchModels
import com.dpal.magpy.features.search.SearchRouter
import com.dpal.magpy.features.search.SearchViewModel
import com.dpal.rawg.RawgService
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

    object Api {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.rawg.io/api/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        private val gson: Gson by lazy {
            GsonBuilder().setDateFormat("yyyy-mm-dd").create()
        }

        internal val gameService: GameService by lazy {
            RawgService(retrofit)
        }
    }

    object Repository {

        internal val game: GameRepository
            get() {
                return GameRepositoryImpl(
                    Api.gameService
                )
            }
    }

    object UseCases {

        val searchForGames: SearchForGamesUseCase
            get() {
                return SearchForGamesUseCase(
                    Repository.game
                )
            }
    }

    object ViewModels {
        val search: SearchViewModel
            get() {
                return SearchViewModel(
                    UseCases.searchForGames
                )
            }
    }

    object Routers {
        val search: SearchRouter
            get() {
                return object : SearchRouter {
                    override fun route(event: SearchModels.Event) {
                        when (event) {
                            is SearchModels.Event.GameClicked ->
                                appCoordinator.state =
                                    AppState.Details(event.gameId)
                        }
                    }
                }
            }
    }

    object Fragments {

        val searchFragment: SearchFragment
            get() {
                return SearchFragment(
                    ViewModels.search,
                    Routers.search
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
}
