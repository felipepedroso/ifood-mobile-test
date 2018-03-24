package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import io.reactivex.Completable
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class StoreUserToSyncOnPreferences(
        private val scheduler: Scheduler,
        private val applicationSettings: ApplicationSettings) {

    fun execute(username: String) {
        Completable.fromAction { applicationSettings.storeUsernameToSync(username) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe()
    }
}