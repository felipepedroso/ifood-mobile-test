package br.pedroso.tweetsentiment.presentation.features.home.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.errors.UiError
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser

class HomeSyncUser(
    private val syncUser: SyncUser,
    private val applicationSettings: ApplicationSettings
) {

    suspend fun execute(username: String): User {
        if (username.isBlank()) {
            throw UiError.EmptyField()
        }

        return syncUser.execute(username).also {
            applicationSettings.storeUsernameToSync(it.userName)
        }
    }
}
