package br.pedroso.tweetsentiment.app.di.presentation.features

import br.pedroso.tweetsentiment.app.di.DependenciesTags.Companion.UI_SCHEDULER
import br.pedroso.tweetsentiment.app.di.DependenciesTags.Companion.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewModel
import br.pedroso.tweetsentiment.presentation.features.home.presenters.HomeSyncRegisteredUserPresenter
import br.pedroso.tweetsentiment.presentation.features.home.presenters.HomeSyncUserPresenter
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncRegisteredUser
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import br.pedroso.tweetsentiment.presentation.features.home.usecases.ValidateUsername
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope

/**
 * Created by felip on 08/03/2018.
 */
class HomeModule {
    val graph = Kodein.Module {
        bind<HomeSyncRegisteredUser>() with singleton {
            HomeSyncRegisteredUser(
                    scheduler = instance(WORKER_SCHEDULER),
                    applicationSettings = instance(),
                    homeSyncUser = instance()
            )
        }

        bind<HomeSyncUser>() with singleton {
            HomeSyncUser(
                    scheduler = instance(WORKER_SCHEDULER),
                    validateUsername = instance(),
                    syncUser = instance(),
                    storeUserToSyncOnPreferences = instance()
            )
        }

        bind<HomeViewModel>() with provider {
            HomeViewModel(
                    homeSyncUser = instance(),
                    homeSyncRegisteredUser = instance(),
                    uiScheduler = instance(UI_SCHEDULER)
            )
        }

        bind<HomeSyncUserPresenter>() with scopedSingleton(androidActivityScope) {
            HomeSyncUserPresenter(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it
            )
        }

        bind<HomeSyncRegisteredUserPresenter>() with scopedSingleton(androidActivityScope) {
            HomeSyncRegisteredUserPresenter(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it
            )
        }

        bind<ValidateUsername>() with singleton {
            ValidateUsername(
                    scheduler = instance(WORKER_SCHEDULER)
            )
        }
    }
}