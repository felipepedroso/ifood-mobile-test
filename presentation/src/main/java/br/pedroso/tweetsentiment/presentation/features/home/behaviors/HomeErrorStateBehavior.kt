package br.pedroso.tweetsentiment.presentation.features.home.behaviors

import br.pedroso.tweetsentiment.domain.errors.UiError
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.errorState.ErrorStateBehavior
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Scheduler

/**
 * Created by felip on 11/02/2018.
 */
class HomeErrorStateBehavior(private val uiScheduler: Scheduler, private val view: Any) : ErrorStateBehavior(uiScheduler, view) {
    override fun apply(upstream: Observable<Any>): ObservableSource<Any> {
        if (view is HomeView) {
            return upstream
                    .subscribeOn(uiScheduler)
                    .doOnSubscribe {
                        subscribeAndFireAction(view.hideErrorState())
                        subscribeAndFireAction(view.hideEmptyUsernameErrorMessage())
                    }
                    .doOnError(this::evaluateError)
        }

        return upstream
    }

    private fun evaluateError(error: Throwable) {
        if (error is UiError.EmptyField) {
            subscribeAndFireAction((view as HomeView).showEmptyUsernameErrorMessage())
        } else {
            subscribeAndFireAction((view as HomeView).showErrorState(error))
        }
    }
}