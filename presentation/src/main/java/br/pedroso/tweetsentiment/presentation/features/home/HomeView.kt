package br.pedroso.tweetsentiment.presentation.features.home

import br.pedroso.tweetsentiment.presentation.shared.behaviors.errorState.ErrorStateView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingContentView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.userNotFoundState.UserNotFoundStateView
import io.reactivex.functions.Action

/**
 * Created by felip on 08/03/2018.
 */
interface HomeView : UserNotFoundStateView, LoadingContentView, ErrorStateView {
    fun hideEmptyUsernameErrorMessage(): Action
    fun showEmptyUsernameErrorMessage(): Action
    fun getUsernameFromInput(): String
    fun openTweetListScreen()
}