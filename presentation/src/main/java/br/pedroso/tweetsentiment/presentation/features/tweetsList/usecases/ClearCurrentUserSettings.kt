package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings

class ClearCurrentUserSettings(
    private val applicationSettings: ApplicationSettings
) {

    fun execute() {
        return applicationSettings.cleanUsernameToSync()
    }
}
