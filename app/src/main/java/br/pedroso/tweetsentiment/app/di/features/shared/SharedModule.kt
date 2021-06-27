package br.pedroso.tweetsentiment.app.di.features.shared

import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import br.pedroso.tweetsentiment.features.tweetslist.usecases.SyncUserTweets
import org.koin.dsl.module

val sharedModule = module {

    single {
        br.pedroso.tweetsentiment.features.tweetslist.usecases.SyncUserTweets(
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
