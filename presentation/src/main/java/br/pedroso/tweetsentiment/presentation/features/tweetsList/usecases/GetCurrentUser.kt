package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import kotlinx.coroutines.flow.Flow

class GetCurrentUser(
    private val databaseDataSource: DatabaseDataSource,
    private val applicationSettings: ApplicationSettings
) {

    fun execute(): Flow<User> {
        val username = applicationSettings.retrieveUsernameToSync()
        return databaseDataSource.getUser(username)
    }
}
