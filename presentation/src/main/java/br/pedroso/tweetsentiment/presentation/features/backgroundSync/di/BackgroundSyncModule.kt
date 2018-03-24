package br.pedroso.tweetsentiment.presentation.features.backgroundSync.di

import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.features.backgroundSync.BackgroundSyncCoordinator
import br.pedroso.tweetsentiment.presentation.features.backgroundSync.usecases.BackgroundSync
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidServiceScope

/**
 * Created by felip on 11/03/2018.
 */
class BackgroundSyncModule {
    val graph = Kodein.Module {
        bind<BackgroundSync>() with singleton {
            BackgroundSync(
                    scheduler = instance(WORKER_SCHEDULER),
                    applicationSettings = instance(),
                    syncUser = instance(),
                    syncUserTweets = instance()
            )
        }

        bind<BackgroundSyncCoordinator>() with scopedSingleton(androidServiceScope) {
            BackgroundSyncCoordinator(
                    workerScheduler = instance(WORKER_SCHEDULER),
                    backgroundSync = instance()
            )
        }
    }
}