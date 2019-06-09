package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetTweetsFromCurrentUser(
        private val scheduler: Scheduler,
        private val getCurrentUser: GetCurrentUser,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(): Flowable<List<Tweet>> {
        return getCurrentUser
                .execute()
                .flatMap { databaseDataSource.getTweetsFromUser(it) }
                .subscribeOn(scheduler)
    }
}