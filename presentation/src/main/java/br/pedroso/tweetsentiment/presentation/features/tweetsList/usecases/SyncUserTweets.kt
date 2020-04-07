package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import io.reactivex.ObservableSource
import kotlinx.coroutines.rx2.rxObservable

class SyncUserTweets(
    private val databaseDataSource: DatabaseDataSource,
    private val twitterDataSource: TwitterDataSource
) {

    fun execute(user: User): ObservableSource<Tweet> {
        return rxObservable {
            try {
                val lastestTweetOnDatabase = databaseDataSource.getLatestTweetFromUser(user)

                val tweets = if (lastestTweetOnDatabase != null) {
                    twitterDataSource.getTweetsSinceTweet(user, lastestTweetOnDatabase)
                } else {
                    twitterDataSource.getLatestTweetsFromUser(user)
                }

                tweets.forEach { tweet ->
                    databaseDataSource.registerTweet(user, tweet)
                    send(tweet)
                }
                close()
            } catch (exception: Exception) {
                close(exception)
            }
        }
    }
}
