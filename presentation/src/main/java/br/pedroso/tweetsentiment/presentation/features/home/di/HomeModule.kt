package br.pedroso.tweetsentiment.presentation.features.home.di

import br.pedroso.tweetsentiment.domain.di.DependenciesTags
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.UI_SCHEDULER
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.home.HomeBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.home.HomePresenter
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSync
import br.pedroso.tweetsentiment.presentation.features.home.usecases.ValidateUsername
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope

/**
 * Created by felip on 08/03/2018.
 */
class HomeModule {
    val graph = Kodein.Module {
        bind<HomeBehaviorCoordinator>() with autoScopedSingleton(androidActivityScope) {
            HomeBehaviorCoordinator(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it
            )
        }

        bind<HomePresenter>() with scopedSingleton(androidActivityScope) {
            HomePresenter(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it as HomeView,
                    homeBehaviorCoordinator = instance(),
                    homeSync = instance()
            )
        }

        bind<HomeSync>() with singleton {
            HomeSync(
                    scheduler = instance(WORKER_SCHEDULER),
                    validateUsername = instance(),
                    syncUser = instance(),
                    storeUserToSyncOnPreferences = instance()
            )
        }

        bind<ValidateUsername>() with singleton {
            ValidateUsername(
                    scheduler = instance(WORKER_SCHEDULER)
            )
        }
    }
}