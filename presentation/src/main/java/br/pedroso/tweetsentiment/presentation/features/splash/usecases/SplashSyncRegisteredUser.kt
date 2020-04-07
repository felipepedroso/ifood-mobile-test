package br.pedroso.tweetsentiment.presentation.features.splash.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import io.reactivex.Observable

class SplashSyncRegisteredUser(
    private val applicationSettings: ApplicationSettings,
    private val homeSyncUser: HomeSyncUser
) {

    suspend fun execute(): User? {
        val usernameToSync = applicationSettings.retrieveUsernameToSync()

        return if(usernameToSync.isNotBlank()) {
            homeSyncUser.execute(usernameToSync)
        } else {
            null
        }
    }
}
