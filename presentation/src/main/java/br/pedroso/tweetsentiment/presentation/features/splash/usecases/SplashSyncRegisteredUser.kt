package br.pedroso.tweetsentiment.presentation.features.splash.usecases

import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import io.reactivex.Observable
import io.reactivex.Scheduler

class SplashSyncRegisteredUser(
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