package br.pedroso.tweetsentiment.presentation.features.backgroundSync.usecases

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import br.pedroso.tweetsentiment.presentation.shared.usecases.SyncUserTweets
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import timber.log.Timber

/**
 * Created by felip on 11/03/2018.
 */
class BackgroundSync(
        private val scheduler: Scheduler,
        private val applicationPreferences: ApplicationPreferences,
        private val syncUser: SyncUser,
        private val syncUserTweets: SyncUserTweets) {

    fun execute(): Completable {
        return Observable.just(applicationPreferences.retrieveUsernameToSync())
                .flatMap {
                    when (it) {
                        "" -> Observable.empty()
                        else -> syncUser.execute(it)
                    }
                }
                .doOnNext { Timber.d("Loaded user: $it") }
                .flatMap { syncUserTweets.execute(it) }
                .doOnNext { Timber.d("Loaded tweet: $it")}
                .ignoreElements()
                .subscribeOn(scheduler)
    }
}