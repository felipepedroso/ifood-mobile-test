package br.pedroso.tweetsentiment.presentation.shared.behaviors.userNotFoundState

import io.reactivex.functions.Action

/**
 * Created by felip on 09/03/2018.
 */
interface UserNotFoundStateView {
    fun showUserNotFoundState(): Action

    fun hideUserNotFoundState(): Action
}