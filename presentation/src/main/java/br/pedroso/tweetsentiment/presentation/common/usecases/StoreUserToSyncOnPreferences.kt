package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import io.reactivex.Completable
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class StoreUserToSyncOnPreferences(
        private val scheduler: Scheduler,
        private val applicationPreferences: ApplicationPreferences) {

    fun execute(username: String) {
        Completable.fromAction { applicationPreferences.storeUsernameToSync(username) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe()
    }
}