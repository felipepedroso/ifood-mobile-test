package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import br.pedroso.tweetsentiment.presentation.shared.usecases.SyncUserTweets
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class FirstSync(
        private val scheduler: Scheduler,
        private val syncUser: SyncUser,
        private val syncUserTweets: SyncUserTweets,
        private val applicationSettings: ApplicationSettings) {

    fun execute(): Completable {
        return Observable.just(applicationSettings.retrieveUsernameToSync())
                .flatMap { syncUser.execute(it) }
                .flatMap { syncUserTweets.execute(it) }
                .ignoreElements()
                .subscribeOn(scheduler)

    }
}