package br.pedroso.tweetsentiment.presentation.features.home.presenters

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.Presenter
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingPresenter
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingContentView
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer
import io.reactivex.Scheduler

class HomeSyncUserPresenter(
        private val uiScheduler: Scheduler,
        private val view: Any) : CompletableTransformer, Presenter(uiScheduler) {

    override fun apply(upstream: Completable): CompletableSource {
        return upstream
                .compose(HomeErrorStatePresenter<User>(uiScheduler, view as HomeView))
                .compose(LoadingPresenter<User>(uiScheduler, view as LoadingContentView))
                .doOnComplete { this.handleSyncComplete() }
    }

    private fun handleSyncComplete() {
        if (view is HomeView) {
            subscribeAndFireAction(view.openTweetListScreen())
        }
    }
}