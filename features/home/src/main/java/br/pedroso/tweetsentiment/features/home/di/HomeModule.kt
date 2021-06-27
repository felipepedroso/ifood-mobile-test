package br.pedroso.tweetsentiment.features.home.di

import br.pedroso.tweetsentiment.features.home.HomeViewModel
import br.pedroso.tweetsentiment.presentation.common.usecases.HomeSyncUser
import org.koin.androidx.viewmodel.dsl.viewModel
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
