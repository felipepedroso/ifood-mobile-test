package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler

class SyncUserData(
    private val syncUser: SyncUser,
    private val syncUserTweets: SyncUserTweets,
    private val applicationSettings: ApplicationSettings
) {

    fun execute(): Completable {
        return Observable.just(applicationSettings.retrieveUsernameToSync())
            .flatMap { syncUser.execute(it) }
            .flatMap { syncUserTweets.execute(it) }
    .ignoreElements()
    }
}
