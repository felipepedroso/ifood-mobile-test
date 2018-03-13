package br.pedroso.tweetsentiment.presentation.features.tweetsList

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.shared.behaviors.emptyState.EmptyStateView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.errorState.ErrorStateView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingContentView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.userNotFoundState.UserNotFoundStateView
import io.reactivex.functions.Action

/**
 * Created by felip on 10/03/2018.
 */
interface TweetsListView : LoadingContentView, EmptyStateView, ErrorStateView, UserNotFoundStateView {
    fun showUserProfile(user: User): Action
    fun showTweet(tweet: Tweet): Action
    fun askConfirmationToSelectOtherUser()
    fun navigateToApplicationHome()
    fun showNaturalLanguageApiError(): Action
}