package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.asFlowable

class GetCurrentUser(
    private val databaseDataSource: DatabaseDataSource,
    private val applicationSettings: ApplicationSettings
) {

    fun execute(): Flowable<User> {
        val username = applicationSettings.retrieveUsernameToSync()
        return databaseDataSource.getUser(username).asFlowable()
    }
}
