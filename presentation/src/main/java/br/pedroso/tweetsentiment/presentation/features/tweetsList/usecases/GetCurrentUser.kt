package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetCurrentUser(
    private val databaseDataSource: DatabaseDataSource,
    private val applicationSettings: ApplicationSettings
) {

    fun execute(): Flowable<User> {
        return Flowable.just(applicationSettings.retrieveUsernameToSync())
            .flatMap { databaseDataSource.getUser(it) }
    }
}
