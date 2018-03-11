package br.pedroso.tweetsentiment.presentation.features.backgroundSync

import br.pedroso.tweetsentiment.presentation.features.backgroundSync.usecases.BackgroundSync
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by felip on 11/03/2018.
 */
class BackgroundSyncCoordinator(
        private val workerScheduler: Scheduler,
        private val backgroundSync: BackgroundSync) {
    private val compositeDisposable by lazy { CompositeDisposable() }

    fun stopSyncing(): Boolean {
        releaseSubscriptions()
        return false
    }

    private fun releaseSubscriptions() {
        compositeDisposable.clear()
    }

    fun startSyncing(): Boolean {
        val subscription = backgroundSync.execute()
                .observeOn(workerScheduler)
                .doOnSubscribe { Timber.d("Background sync has started!") }
                .subscribe(
                        { Timber.d("Background sync has completed!") },
                        { Timber.e(it) }
                )

        compositeDisposable.add(subscription)

        return true
    }
}