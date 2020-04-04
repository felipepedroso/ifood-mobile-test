package br.pedroso.tweetsentiment.app.di.features.shared

import br.pedroso.tweetsentiment.app.di.DependenciesTags.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.common.usecases.GetUserFromApi
import br.pedroso.tweetsentiment.presentation.common.usecases.GetUserFromDatabase
import br.pedroso.tweetsentiment.presentation.common.usecases.GetUserRecordOnDatabase
import br.pedroso.tweetsentiment.presentation.common.usecases.RegisterUserOnDatabase
import br.pedroso.tweetsentiment.presentation.common.usecases.StoreUserToSyncOnPreferences
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserLatestTweetOnDatabase
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserLatestTweetsFromApi
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserTweetsSinceTweet
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.RegisterTweetOnDatabase
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.SyncUserTweets
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    single {
        GetUserFromApi(
            scheduler = get(named(WORKER_SCHEDULER)),
            twitterDataSource = get()
        )
    }

    single {
        GetUserFromDatabase(
            scheduler = get(named(WORKER_SCHEDULER)),
            databaseDataSource = get()
        )
    }

    single {
        GetUserLatestTweetOnDatabase(
            scheduler = get(named(WORKER_SCHEDULER)),
            databaseDataSource = get()
        )
    }

    single {
        GetUserLatestTweetsFromApi(
            scheduler = get(named(WORKER_SCHEDULER)),
            twitterDataSource = get()
        )
    }

    single {
        SyncUserTweets(
            scheduler = get(named(WORKER_SCHEDULER)),
            getUserLatestTweetOnDatabase = get(),
            getUserLatestTweetsFromApi = get(),
            getUserTweetsSinceTweet = get(),
            registerTweetOnDatabase = get()
        )
    }

    single {
        SyncUser(
            scheduler = get(named(WORKER_SCHEDULER)),
            registerUserOnDatabase = get(),
            getUserFromApi = get(),
            getUserRecordOnDatabase = get()
        )
    }

    single {
        GetUserRecordOnDatabase(
            scheduler = get(named(WORKER_SCHEDULER)),
            databaseDataSource = get()
        )
    }

    single {
        GetUserTweetsSinceTweet(
            scheduler = get(named(WORKER_SCHEDULER)),
            twitterDataSource = get()
        )
    }

    single {
        RegisterTweetOnDatabase(
            scheduler = get(named(WORKER_SCHEDULER)),
            databaseDataSource = get()
        )
    }

    single {
        RegisterUserOnDatabase(
            scheduler = get(named(WORKER_SCHEDULER)),
            databaseDataSource = get()
        )
    }

    single {
        StoreUserToSyncOnPreferences(
            scheduler = get(named(WORKER_SCHEDULER)),
            applicationSettings = get()
        )
    }
}
