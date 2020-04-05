package br.pedroso.tweetsentiment.app.di.features.splash

import br.pedroso.tweetsentiment.app.di.DependenciesTags.UI_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.splash.SplashViewModel
import br.pedroso.tweetsentiment.presentation.features.splash.usecases.SplashSyncRegisteredUser
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val splashModule = module {
    single {
        SplashSyncRegisteredUser(
            applicationSettings = get(),
            homeSyncUser = get()
        )
    }

    viewModel {
        SplashViewModel(
            splashSyncRegisteredUser = get(),
            uiScheduler = get(named(UI_SCHEDULER))
        )
    }
}
