package br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors

import br.pedroso.tweetsentiment.domain.network.errors.NaturalLanguageApiError
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.presentation.common.behaviors.ErrorStateBehavior
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class TweetsListErrorStateBehavior<T>(
        val uiScheduler: Scheduler,
        private val view: TweetsListView) : ErrorStateBehavior<T>(uiScheduler) {
    override fun executeShowErrorStateActions(error: Throwable) {
        when (error) {
            is TwitterError.UserNotFound -> subscribeAndFireAction(view.showUserNotFoundState())
            is TwitterError.EmptyResponse -> subscribeAndFireAction(view.showEmptyState())
            is NaturalLanguageApiError -> subscribeAndFireAction(view.showNaturalLanguageApiError())
            else -> subscribeAndFireAction(view.showErrorState(error))
        }
    }

    override fun executeHideErrorStateActions() {
        subscribeAndFireAction(view.hideErrorState())
        subscribeAndFireAction(view.hideEmptyState())
        subscribeAndFireAction(view.hideUserNotFoundState())
    }

}