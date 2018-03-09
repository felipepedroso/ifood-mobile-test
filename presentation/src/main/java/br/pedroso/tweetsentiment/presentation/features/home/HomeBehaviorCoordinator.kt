package br.pedroso.tweetsentiment.presentation.features.home

import br.pedroso.tweetsentiment.presentation.features.home.behaviors.HomeErrorStateBehavior
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingBehavior
import br.pedroso.tweetsentiment.presentation.shared.behaviors.userNotFoundState.UserNotFoundStateBehavior
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 08/03/2018.
 */
class HomeBehaviorCoordinator(
        private val uiScheduler: Scheduler,
        private val view: Any) : ObservableTransformer<Any, Any> {
    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        return upstream
                .compose(HomeErrorStateBehavior(uiScheduler, view))
                .compose(UserNotFoundStateBehavior(uiScheduler, view))
                .compose(LoadingBehavior(uiScheduler, view))
    }
}