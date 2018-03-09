package br.pedroso.tweetsentiment.presentation.shared.behaviors.loading

import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class LoadingBehavior(val uiScheduler: Scheduler, val view: Any) : ObservableTransformer<Any, Any>, Behavior(uiScheduler) {
    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        if (view is LoadingContentView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe { subscribeAndFireAction(view.showLoading()) }
                    .doOnNext { subscribeAndFireAction(view.hideLoading()) }
                    .doOnTerminate { subscribeAndFireAction(view.hideLoading()) }
        }

        return upstream
    }

}