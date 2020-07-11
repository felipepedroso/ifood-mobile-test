package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterRepository

class SyncUserTweets(
    private val databaseDataSource: DatabaseDataSource,
    private val twitterRepository: TwitterRepository
) {

    suspend fun execute(user: User) {
        val lastestTweetOnDatabase = databaseDataSource.getLatestTweetFromUser(user)

        val tweets = if (lastestTweetOnDatabase != null) {
            twitterRepository.getTweetsSinceTweet(user, lastestTweetOnDatabase)
        } else {
            twitterRepository.getLatestTweetsFromUser(user)
        }

        tweets.forEach { tweet -> databaseDataSource.registerTweet(user, tweet) }
    }
}
