package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource

class SyncUserTweets(
    private val databaseDataSource: DatabaseDataSource,
    private val twitterDataSource: TwitterDataSource
) {

    suspend fun execute(user: User) {
        val lastestTweetOnDatabase = databaseDataSource.getLatestTweetFromUser(user)

        val tweets = if (lastestTweetOnDatabase != null) {
            twitterDataSource.getTweetsSinceTweet(user, lastestTweetOnDatabase)
        } else {
            twitterDataSource.getLatestTweetsFromUser(user)
        }

        tweets.forEach { tweet -> databaseDataSource.registerTweet(user, tweet) }
    }
}
