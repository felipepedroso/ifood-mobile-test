package br.pedroso.tweetsentiment.presentation.features.home.presenters

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.Presenter
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingContentView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingPresenter
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

class HomeSyncRegisteredUserPresenter(
        private val uiScheduler: Scheduler,
        private val view: Any) : ObservableTransformer<User, User>, Presenter(uiScheduler) {

    override fun apply(upstream: Observable<User>): ObservableSource<User> {
        return upstream
                .compose(HomeErrorStatePresenter<User>(uiScheduler, view as HomeView))
                .compose(LoadingPresenter<User>(uiScheduler, view as LoadingContentView))
                .doOnNext { handleSyncComplete() }
    }

    private fun handleSyncComplete() {
        if (view is HomeView) {
            subscribeAndFireAction(view.openTweetListScreen())
        }
    }
}