package br.pedroso.tweetsentiment.presentation.shared.usecases.di

import br.pedroso.tweetsentiment.domain.di.DependenciesTags
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.WORKER_SCHEDULER
import br.pedroso.tweetsentiment.presentation.common.usecases.GetUserRecordOnDatabase
import br.pedroso.tweetsentiment.presentation.common.usecases.StoreUserToSyncOnPreferences
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import br.pedroso.tweetsentiment.presentation.shared.usecases.*
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by felip on 09/03/2018.
 */
class CommonUsecasesModule {
    val graph = Kodein.Module {
        bind<GetUserFromApi>() with singleton {
            GetUserFromApi(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    twitterDataSource = instance()
            )
        }

        bind<GetUserFromDatabase>() with singleton {
            GetUserFromDatabase(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    databaseDataSource = instance()
            )
        }

        bind<GetUserLatestTweetOnDatabase>() with singleton {
            GetUserLatestTweetOnDatabase(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    databaseDataSource = instance()
            )
        }

        bind<GetUserLatestTweetsFromApi>() with singleton {
            GetUserLatestTweetsFromApi(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    twitterDataSource = instance()
            )
        }

        bind<SyncUserTweets>() with singleton {
            SyncUserTweets(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    getUserLatestTweetOnDatabase = instance(),
                    getUserLatestTweetsFromApi = instance(),
                    getUserTweetsSinceTweet = instance(),
                    registerTweetOnDatabase = instance()
            )
        }

        bind<SyncUser>() with singleton {
            SyncUser(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    registerUserOnDatabase = instance(),
                    getUserFromApi = instance(),
                    getUserRecordOnDatabase = instance()
            )
        }

        bind<GetUserRecordOnDatabase>() with singleton {
            GetUserRecordOnDatabase(
                    scheduler = instance(WORKER_SCHEDULER),
                    databaseDataSource = instance()
            )
        }

        bind<GetUserTweetsSinceTweet>() with singleton {
            GetUserTweetsSinceTweet(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    twitterDataSource = instance()
            )
        }

        bind<RegisterTweetOnDatabase>() with singleton {
            RegisterTweetOnDatabase(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    databaseDataSource = instance()
            )
        }

        bind<RegisterUserOnDatabase>() with singleton {
            RegisterUserOnDatabase(
                    scheduler = instance(DependenciesTags.WORKER_SCHEDULER),
                    databaseDataSource = instance()
            )
        }

        bind<StoreUserToSyncOnPreferences>() with singleton {
            StoreUserToSyncOnPreferences(
                    scheduler = instance(WORKER_SCHEDULER),
                    applicationPreferences = instance()
            )
        }
    }
}