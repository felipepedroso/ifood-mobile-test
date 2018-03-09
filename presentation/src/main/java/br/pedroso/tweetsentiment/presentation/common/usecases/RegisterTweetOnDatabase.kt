package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Completable
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class RegisterTweetOnDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(user: User, tweet: Tweet) {
        Completable.fromAction { databaseDataSource.registerTweet(user, tweet) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe()
    }
}