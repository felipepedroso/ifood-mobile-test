package br.pedroso.tweetsentiment.app.di.presentation

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
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

class CommonUsecasesModule {
    val graph = Kodein.Module {
        bind<GetUserFromApi>() with singleton {
            GetUserFromApi(
                scheduler = instance(WORKER_SCHEDULER),
                twitterDataSource = instance()
            )
        }

        bind<GetUserFromDatabase>() with singleton {
            GetUserFromDatabase(
                scheduler = instance(WORKER_SCHEDULER),
                databaseDataSource = instance()
            )
        }

        bind<GetUserLatestTweetOnDatabase>() with singleton {
            GetUserLatestTweetOnDatabase(
                scheduler = instance(WORKER_SCHEDULER),
                databaseDataSource = instance()
            )
        }

        bind<GetUserLatestTweetsFromApi>() with singleton {
            GetUserLatestTweetsFromApi(
                scheduler = instance(WORKER_SCHEDULER),
                twitterDataSource = instance()
            )
        }

        bind<SyncUserTweets>() with singleton {
            SyncUserTweets(
                scheduler = instance(WORKER_SCHEDULER),
                getUserLatestTweetOnDatabase = instance(),
                getUserLatestTweetsFromApi = instance(),
                getUserTweetsSinceTweet = instance(),
                registerTweetOnDatabase = instance()
            )
        }

        bind<SyncUser>() with singleton {
            SyncUser(
                scheduler = instance(WORKER_SCHEDULER),
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
                scheduler = instance(WORKER_SCHEDULER),
                twitterDataSource = instance()
            )
        }

        bind<RegisterTweetOnDatabase>() with singleton {
            RegisterTweetOnDatabase(
                scheduler = instance(WORKER_SCHEDULER),
                databaseDataSource = instance()
            )
        }

        bind<RegisterUserOnDatabase>() with singleton {
            RegisterUserOnDatabase(
                scheduler = instance(WORKER_SCHEDULER),
                databaseDataSource = instance()
            )
        }

        bind<StoreUserToSyncOnPreferences>() with singleton {
            StoreUserToSyncOnPreferences(
                scheduler = instance(WORKER_SCHEDULER),
                applicationSettings = instance()
            )
        }
    }
}
