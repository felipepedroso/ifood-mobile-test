package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import io.reactivex.Completable

class ClearCurrentUserSettings(
    private val applicationSettings: ApplicationSettings
) {

    fun execute(): Completable {
        return Completable.fromCallable { applicationSettings.cleanUsernameToSync() }
    }
}
