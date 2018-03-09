package br.pedroso.tweetsentiment.presentation.shared.behaviors.userNotFoundState

import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class UserNotFoundStateBehavior(val uiScheduler: Scheduler, val view: Any) : ObservableTransformer<Any, Any>, Behavior(uiScheduler) {
    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        if (view is UserNotFoundStateView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe { subscribeAndFireAction(view.hideUserNotFoundState()) }
                    .doOnError(this::evaluateError)
        }

        return upstream
    }

    private fun evaluateError(error: Throwable) {
        if (error is TwitterError.UserNotFound) {
            subscribeAndFireAction((view as UserNotFoundStateView).showUserNotFoundState())
        }
    }
}