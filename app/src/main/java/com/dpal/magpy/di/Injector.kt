package com.dpal.magpy.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dpal.data.tag.Tag
import com.dpal.data.tag.TagCache
import com.dpal.data.tag.TagRepository
import com.dpal.domain.details.GetGameDetailsUseCase
import com.dpal.domain.details.RemoveGameTagUseCase
import com.dpal.domain.details.SaveGameTagUseCase
import com.dpal.domain.search.SearchForGamesUseCase
import com.dpal.games.data.GameRepository
import com.dpal.games.data.GameRepositoryImpl
import com.dpal.games.data.GameService
import com.dpal.magpy.AppCoordinator
import com.dpal.magpy.AppState
import com.dpal.magpy.MainActivity
import com.dpal.magpy.features.details.GameDetailsFragment
import com.dpal.magpy.features.details.GameDetailsViewModel
import com.dpal.magpy.features.search.SearchFragment
import com.dpal.magpy.features.search.SearchModels
import com.dpal.magpy.features.search.SearchRouter
import com.dpal.magpy.features.search.SearchViewModel
import com.dpal.magpy.tagcache.InMemoryTagCache
import com.dpal.rawg.RawgService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    lateinit var appCoordinator: AppCoordinator
    lateinit var appContext: Context

    fun init(
        activity: MainActivity
    ) {
        appContext = activity.applicationContext
        appCoordinator = AppCoordinator(activity.supportFragmentManager)
    }

    object Api {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.rawg.io/api/")
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            ChuckerInterceptor.Builder(appContext)
                                .build()
                        )
                        .build()
                )
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

    object Cache {
        internal val tag: TagCache by lazy {
            InMemoryTagCache()
        }
    }

    object Repository {

        internal val game: GameRepository
            get() {
                return GameRepositoryImpl(
                    Api.gameService
                )
            }

        internal val tag: TagRepository
            get() {
                return TagRepository.instance(
                    cache = Cache.tag
                )
            }
    }

    object UseCases {

        internal val searchForGames: SearchForGamesUseCase
            get() {
                return SearchForGamesUseCase(
                    Repository.game
                )
            }

        internal val getGameDetails: GetGameDetailsUseCase
            get() {
                return GetGameDetailsUseCase(
                    Repository.game,
                    Repository.tag
                )
            }

        internal val saveGameTag: SaveGameTagUseCase
            get() {
                return SaveGameTagUseCase(
                    Repository.tag
                )
            }

        internal val removeGameTag: RemoveGameTagUseCase
            get() {
                return RemoveGameTagUseCase(
                    Repository.tag
                )
            }
    }

    object ViewModels {
        internal val search: SearchViewModel
            get() {
                return SearchViewModel(
                    UseCases.searchForGames
                )
            }

        internal fun gameDetails(id: String): GameDetailsViewModel {
            return GameDetailsViewModel(
                id,
                UseCases.getGameDetails,
                UseCases.saveGameTag,
                UseCases.removeGameTag
            )
        }
    }

    object Routers {
        internal val search: SearchRouter
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
            id: String
        ): GameDetailsFragment {
            return GameDetailsFragment(
                ViewModels.gameDetails(id)
            )
        }
    }
}
