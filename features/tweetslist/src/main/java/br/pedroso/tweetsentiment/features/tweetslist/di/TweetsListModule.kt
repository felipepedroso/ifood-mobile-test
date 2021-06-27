package br.pedroso.tweetsentiment.features.tweetslist.di

import br.pedroso.tweetsentiment.features.tweetslist.TweetsListViewModel
import br.pedroso.tweetsentiment.features.tweetslist.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.features.tweetslist.usecases.ClearCurrentUserSettings
import br.pedroso.tweetsentiment.features.tweetslist.usecases.GetCurrentUser
import br.pedroso.tweetsentiment.features.tweetslist.usecases.GetTweetsFromCurrentUser
import br.pedroso.tweetsentiment.features.tweetslist.usecases.SyncUserData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tweetsListModule = module {
    viewModel {
        TweetsListViewModel(
            syncUserData = get(),
            getCurrentUser = get(),
            getTweetsFromCurrentUser = get(),
            analyseTweetSentiment = get(),
            clearCurrentUserSettings = get()
        )
    }

    single {
        ClearCurrentUserSettings(
            applicationSettings = get()
        )
    }

    single {
        SyncUserData(
            applicationSettings = get(),
            syncUser = get(),
            syncUserTweets = get()
        )
    }

    single {
        GetCurrentUser(
            applicationSettings = get(),
            databaseDataSource = get()
        )
    }

    single {
        GetTweetsFromCurrentUser(
            databaseDataSource = get(),
            getCurrentUser = get()
        )
    }

    single {
        AnalyseTweetSentiment(
            sentimentAnalysisDataSource = get(),
            databaseDataSource = get()
        )
    }
}
