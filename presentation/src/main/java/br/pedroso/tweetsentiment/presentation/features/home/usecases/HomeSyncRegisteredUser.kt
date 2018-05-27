package br.pedroso.tweetsentiment.presentation.features.home.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import io.reactivex.Observable
import io.reactivex.Scheduler

class HomeSyncRegisteredUser(
        private val scheduler: Scheduler,
        private val applicationSettings: ApplicationSettings,
        private val homeSyncUser: HomeSyncUser) {

    fun execute(): Observable<User> {
        val usernameToSync = applicationSettings.retrieveUsernameToSync()

        return Observable.just(usernameToSync)
                .flatMap {
                    when (it) {
                        "" -> Observable.empty()
                        else -> homeSyncUser.execute(it)
                    }
                }
                .subscribeOn(scheduler)
    }
}