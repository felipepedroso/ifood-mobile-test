package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetCurrentUser(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource,
        private val applicationSettings: ApplicationSettings) {

    fun execute(): Flowable<User> {
        return Flowable.just(applicationSettings.retrieveUsernameToSync())
                .flatMap { databaseDataSource.getUser(it) }
                .subscribeOn(scheduler)
    }
}