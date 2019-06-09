package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Completable
import io.reactivex.Scheduler

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