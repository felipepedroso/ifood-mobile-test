package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.domain.utils.Optional
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableSource

class SyncUserTweets(
    private val databaseDataSource: DatabaseDataSource,
    private val twitterDataSource: TwitterDataSource
) {

    private fun getLatestTweetFromDatabase(user: User): Maybe<Optional<Tweet>> {
        return databaseDataSource.getLatestTweetFromUser(user)
            .map { Optional.of(it) }
            .defaultIfEmpty(Optional.empty())
    }

    fun execute(user: User): ObservableSource<Tweet> {
        return getLatestTweetFromDatabase(user)
            .flatMapObservable { optional ->
                val latestTweet = optional.value

                twitterDataSource.run {
                    if (latestTweet != null) {
                        getTweetsSinceTweet(user, latestTweet)
                    } else {
                        getLatestTweetsFromUser(user)
                    }
                }
            }
            .flatMap { tweet ->
                databaseDataSource.registerTweet(user, tweet).andThen(Observable.just(tweet))
            }
    }
}
