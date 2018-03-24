package br.pedroso.tweetsentiment.presentation.features.tweetsList.di

import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.UI_SCHEDULER
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListPresenter
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.AnalyseTweetSentimentBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.FirstSyncBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.UserFlowableBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.UserTweetsFlowableBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.FirstSync
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserFlowableFromDatabase
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserTweetsFlowableFromDatabase
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope

/**
 * Created by felip on 10/03/2018.
 */
class TweetsListModule {
    val graph = Kodein.Module {

        bind<TweetsListPresenter>() with scopedSingleton(androidActivityScope) {
            TweetsListPresenter(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it as TweetsListView,
                    applicationSettings = instance(),
                    firstSync = instance(),
                    firstSyncBehaviorCoordinator = instance(),
                    getUserFlowableFromDatabase = instance(),
                    getUserTweetsFlowableFromDatabase = instance(),
                    userFlowableBehaviorCoordinator = instance(),
                    userTweetsFlowableBehaviorCoordinator = instance(),
                    analyseTweetSentiment = instance(),
                    analyseTweetSentimentBehaviorCoordinator = instance(),
                    backgroundSyncScheduler = instance()
            )
        }

        bind<FirstSync>() with singleton {
            FirstSync(
                    scheduler = instance(WORKER_SCHEDULER),
                    applicationSettings = instance(),
                    syncUser = instance(),
                    syncUserTweets = instance()
            )
        }

        bind<FirstSyncBehaviorCoordinator>() with autoScopedSingleton(androidActivityScope) {
            FirstSyncBehaviorCoordinator(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it as TweetsListView
            )
        }

        bind<GetUserFlowableFromDatabase>() with singleton {
            GetUserFlowableFromDatabase(
                    scheduler = instance(WORKER_SCHEDULER),
                    applicationSettings = instance(),
                    databaseDataSource = instance()
            )
        }

        bind<UserFlowableBehaviorCoordinator>() with autoScopedSingleton(androidActivityScope) {
            UserFlowableBehaviorCoordinator(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it as TweetsListView
            )
        }

        bind<GetUserTweetsFlowableFromDatabase>() with singleton {
            GetUserTweetsFlowableFromDatabase(
                    scheduler = instance(WORKER_SCHEDULER),
                    databaseDataSource = instance()
            )
        }

        bind<UserTweetsFlowableBehaviorCoordinator>() with autoScopedSingleton(androidActivityScope) {
            UserTweetsFlowableBehaviorCoordinator(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it as TweetsListView
            )
        }

        bind<AnalyseTweetSentiment>() with singleton {
            AnalyseTweetSentiment(
                    scheduler = instance(WORKER_SCHEDULER),
                    sentimentAnalysisDataSource = instance(),
                    databaseDataSource = instance()
            )
        }

        bind<AnalyseTweetSentimentBehaviorCoordinator>() with autoScopedSingleton(androidActivityScope) {
            AnalyseTweetSentimentBehaviorCoordinator(
                    uiScheduler = instance(UI_SCHEDULER),
                    view = it as TweetsListView
            )
        }
    }
}