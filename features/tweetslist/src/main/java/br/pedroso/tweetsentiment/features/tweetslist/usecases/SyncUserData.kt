package br.pedroso.tweetsentiment.features.tweetslist.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser

class SyncUserData(
    private val syncUser: SyncUser,
    private val syncUserTweets: SyncUserTweets,
    private val applicationSettings: ApplicationSettings
) {

    suspend fun execute() {
        val usernameToSync = applicationSettings.retrieveUsernameToSync()

        val user = syncUser.execute(usernameToSync)

        syncUserTweets.execute(user)
    }
}
