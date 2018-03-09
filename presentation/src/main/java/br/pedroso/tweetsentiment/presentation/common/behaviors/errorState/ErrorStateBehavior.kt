package br.pedroso.tweetsentiment.presentation.shared.behaviors.errorState

import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
open class ErrorStateBehavior(private val uiScheduler: Scheduler, private val view: Any) : ObservableTransformer<Any, Any>, Behavior(uiScheduler) {
    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        if (view is ErrorStateView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe { subscribeAndFireAction(view.hideErrorState()) }
                    .doOnError { subscribeAndFireAction(view.showErrorState(it)) }
        }

        return upstream
    }
}