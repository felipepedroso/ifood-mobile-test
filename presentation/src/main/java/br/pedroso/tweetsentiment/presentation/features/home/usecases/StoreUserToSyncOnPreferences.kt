package br.pedroso.tweetsentiment.presentation.features.home.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import io.reactivex.Completable

class StoreUserToSyncOnPreferences(
    private val applicationSettings: ApplicationSettings
) {

    fun execute(username: String): Completable {
        return Completable.fromAction { applicationSettings.storeUsernameToSync(username) }
    }
}
