package br.pedroso.tweetsentiment.presentation.features.home.behaviors

import br.pedroso.tweetsentiment.domain.errors.UiError
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.presentation.common.behaviors.ErrorStateBehavior
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import io.reactivex.Scheduler

/**
 * Created by felip on 11/02/2018.
 */
class HomeErrorStateBehavior<T>(
        uiScheduler: Scheduler,
        private val view: HomeView) : ErrorStateBehavior<T>(uiScheduler) {
    override fun executeShowErrorStateActions(error: Throwable) {
        when (error) {
            is UiError.EmptyField -> subscribeAndFireAction(view.showEmptyUsernameErrorMessage())
            is TwitterError.UserNotFound -> subscribeAndFireAction(view.showUserNotFoundState())
            else -> subscribeAndFireAction(view.showErrorState(error))
        }
    }

    override fun executeHideErrorStateActions() {
        subscribeAndFireAction(view.hideEmptyUsernameErrorMessage())
        subscribeAndFireAction(view.hideErrorState())
        subscribeAndFireAction(view.hideUserNotFoundState())
    }
}