package br.pedroso.tweetsentiment.app.di.features.home

import br.pedroso.tweetsentiment.presentation.features.home.HomeViewModel
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single {
        HomeSyncUser(
            syncUser = get(),
            applicationSettings = get()
        )
    }

    viewModel {
        HomeViewModel(
            homeSyncUser = get()
        )
    }
}
