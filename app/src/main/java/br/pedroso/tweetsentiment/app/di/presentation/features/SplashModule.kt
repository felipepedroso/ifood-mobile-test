package br.pedroso.tweetsentiment.app.di.presentation.features

import br.pedroso.tweetsentiment.app.di.DependenciesTags
import br.pedroso.tweetsentiment.presentation.features.splash.SplashViewModel
import br.pedroso.tweetsentiment.presentation.features.splash.usecases.SplashSyncRegisteredUser
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton

class SplashModule {
    val graph = Kodein.Module {
        bind<SplashSyncRegisteredUser>() with singleton {
            SplashSyncRegisteredUser(
                scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                applicationSettings = instance(),
                homeSyncUser = instance()
            )
        }

        bind<SplashViewModel>() with provider {
            SplashViewModel(
                splashSyncRegisteredUser = instance(),
                uiScheduler = instance(DependenciesTags.UI_SCHEDULER)
            )
        }
    }
}
