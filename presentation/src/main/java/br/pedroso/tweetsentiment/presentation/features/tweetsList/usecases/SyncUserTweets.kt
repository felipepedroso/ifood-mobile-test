package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.utils.Result
import io.reactivex.ObservableSource
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class SyncUserTweets(
        private val scheduler: Scheduler,
        private val getUserLatestTweetOnDatabase: GetUserLatestTweetOnDatabase,
        private val getUserTweetsSinceTweet: GetUserTweetsSinceTweet,
        private val getUserLatestTweetsFromApi: GetUserLatestTweetsFromApi,
        private val registerTweetOnDatabase: RegisterTweetOnDatabase) {

    fun execute(user: User): ObservableSource<Tweet> {
        return getUserLatestTweetOnDatabase.execute(user)
                .flatMapObservable {
                    when (it) {
                        is Result.WithValue<*> -> getUserTweetsSinceTweet.execute(user, it.value as Tweet)
                        else -> getUserLatestTweetsFromApi.execute(user)
                    }
                }
                .doOnNext { registerTweetOnDatabase.execute(user, it) }
                .subscribeOn(scheduler)
    }
}