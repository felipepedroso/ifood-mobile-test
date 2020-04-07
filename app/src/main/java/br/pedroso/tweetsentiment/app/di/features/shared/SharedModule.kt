package br.pedroso.tweetsentiment.app.di.features.shared

import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.SyncUserTweets
import org.koin.dsl.module

val sharedModule = module {

    single {
        SyncUserTweets(
            databaseDataSource = get(),
            twitterDataSource = get()
        )
    }

    single {
        SyncUser(
            twitterDataSource = get(),
            databaseDataSource = get()
        )
    }
}
