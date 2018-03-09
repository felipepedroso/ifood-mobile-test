package br.pedroso.tweetsentiment.presentation.features.home

import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSync
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by felip on 08/03/2018.
 */
class HomePresenter(
        private val uiScheduler: Scheduler,
        private val view: HomeView,
        private val homeBehaviorCoordinator: HomeBehaviorCoordinator,
        private val homeSync: HomeSync) {

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun clickedCheckUserTweets() {
        val username = view.getUsernameFromInput()

        executeHomeSync(username)
    }

    private fun executeHomeSync(username: String) {
        val subscription = homeSync.execute(username)
                .compose(homeBehaviorCoordinator)
                .observeOn(uiScheduler)
                .ignoreElements()
                .subscribe(
                        { completedHomeSync() },
                        { Timber.e(it) }
                )

        compositeDisposable.add(subscription)
    }

    private fun completedHomeSync() {
        view.openTweetListScreen()
    }

    fun releaseSubscriptions() {
        compositeDisposable.clear()
    }
}