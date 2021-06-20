package br.pedroso.tweetsentiment.features.splash.di

import br.pedroso.tweetsentiment.features.splash.SplashViewModel
import br.pedroso.tweetsentiment.features.splash.usecases.SplashSyncRegisteredUser
import org.koin.androidx.viewmodel.dsl.viewModel
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
            splashSyncRegisteredUser = get()
        )
    }
}
