package br.pedroso.tweetsentiment.features.tweetslist.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings

class ClearCurrentUserSettings(
    private val applicationSettings: ApplicationSettings
) {

    fun execute() {
        return applicationSettings.cleanUsernameToSync()
    }
}
