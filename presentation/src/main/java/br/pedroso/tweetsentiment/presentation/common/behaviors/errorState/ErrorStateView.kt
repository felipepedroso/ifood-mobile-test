package br.pedroso.tweetsentiment.presentation.shared.behaviors.errorState

import io.reactivex.functions.Action

/**
 * Created by felip on 09/03/2018.
 */
interface ErrorStateView {
    fun showErrorState(it: Throwable): Action

    fun hideErrorState(): Action
}