package br.pedroso.tweetsentiment.app.di.presentation.features

import br.pedroso.tweetsentiment.app.di.DependenciesTags.UI_SCHEDULER
import br.pedroso.tweetsentiment.app.di.DependenciesTags.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewModel
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import br.pedroso.tweetsentiment.presentation.features.home.usecases.ValidateUsername
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton

class HomeModule {
    val graph = Kodein.Module {
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
                uiScheduler = instance(UI_SCHEDULER)
            )
        }

        bind<ValidateUsername>() with singleton {
            ValidateUsername()
        }
    }
}
