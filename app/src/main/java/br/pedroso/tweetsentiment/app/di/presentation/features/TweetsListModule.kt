package br.pedroso.tweetsentiment.app.di.presentation.features

import br.pedroso.tweetsentiment.app.di.DependenciesTags.UI_SCHEDULER
import br.pedroso.tweetsentiment.app.di.DependenciesTags.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListViewModel
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.ClearCurrentUserSettings
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetCurrentUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetTweetsFromCurrentUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.SyncUserData
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton

class TweetsListModule {
    val graph = Kodein.Module {

        bind<TweetsListViewModel>() with provider {
            TweetsListViewModel(
                uiScheduler = instance(UI_SCHEDULER),
                syncUserData = instance(),
                getCurrentUser = instance(),
                getTweetsFromCurrentUser = instance(),
                analyseTweetSentiment = instance(),
                clearCurrentUserSettings = instance()
            )
        }

        bind<ClearCurrentUserSettings>() with singleton {
            ClearCurrentUserSettings(
                scheduler = instance(WORKER_SCHEDULER),
                applicationSettings = instance()
            )
        }

        bind<SyncUserData>() with singleton {
            SyncUserData(
                scheduler = instance(WORKER_SCHEDULER),
                applicationSettings = instance(),
                syncUser = instance(),
                syncUserTweets = instance()
            )
        }

        bind<GetCurrentUser>() with singleton {
            GetCurrentUser(
                scheduler = instance(WORKER_SCHEDULER),
                applicationSettings = instance(),
                databaseDataSource = instance()
            )
        }

        bind<GetTweetsFromCurrentUser>() with singleton {
            GetTweetsFromCurrentUser(
                scheduler = instance(WORKER_SCHEDULER),
                databaseDataSource = instance(),
                getCurrentUser = instance()
            )
        }

        bind<AnalyseTweetSentiment>() with singleton {
            AnalyseTweetSentiment(
                scheduler = instance(WORKER_SCHEDULER),
                sentimentAnalysisDataSource = instance(),
                databaseDataSource = instance()
            )
        }
    }
}
