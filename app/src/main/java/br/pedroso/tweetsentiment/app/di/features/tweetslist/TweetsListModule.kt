package br.pedroso.tweetsentiment.app.di.features.tweetslist

import br.pedroso.tweetsentiment.app.di.DependenciesTags.UI_SCHEDULER
import br.pedroso.tweetsentiment.app.di.DependenciesTags.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListViewModel
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.ClearCurrentUserSettings
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetCurrentUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetTweetsFromCurrentUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.SyncUserData
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tweetsListModule = module {
    viewModel {
        TweetsListViewModel(
            uiScheduler = get(named(UI_SCHEDULER)),
            syncUserData = get(),
            getCurrentUser = get(),
            getTweetsFromCurrentUser = get(),
            analyseTweetSentiment = get(),
            clearCurrentUserSettings = get()
        )
    }

    single {
        ClearCurrentUserSettings(
            scheduler = get(named(WORKER_SCHEDULER)),
            applicationSettings = get()
        )
    }

    single {
        SyncUserData(
            scheduler = get(named(WORKER_SCHEDULER)),
            applicationSettings = get(),
            syncUser = get(),
            syncUserTweets = get()
        )
    }

    single {
        GetCurrentUser(
            scheduler = get(named(WORKER_SCHEDULER)),
            applicationSettings = get(),
            databaseDataSource = get()
        )
    }

    single {
        GetTweetsFromCurrentUser(
            scheduler = get(named(WORKER_SCHEDULER)),
            databaseDataSource = get(),
            getCurrentUser = get()
        )
    }

    single {
        AnalyseTweetSentiment(
            scheduler = get(named(WORKER_SCHEDULER)),
            sentimentAnalysisDataSource = get(),
            databaseDataSource = get()
        )
    }
}
