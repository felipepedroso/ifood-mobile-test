package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import io.reactivex.Completable
import io.reactivex.Scheduler

class ClearCurrentUserSettings(
        private val scheduler: Scheduler,
        private val applicationSettings: ApplicationSettings) {

    fun execute(): Completable {
        return Completable.fromCallable {
            applicationSettings.cleanUsernameToSync()
        }.subscribeOn(scheduler)
    }
}