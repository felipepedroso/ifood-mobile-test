package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class GetUserTweetsSinceTweet(
        private val scheduler: Scheduler,
        private val twitterDataSource: TwitterDataSource) {

    fun execute(user: User, tweet: Tweet): Observable<Tweet> {
        return twitterDataSource.getTweetsSinceTweet(user, tweet)
                .subscribeOn(scheduler)
    }
}