package br.pedroso.tweetsentiment.presentation.shared.behaviors.loading

import io.reactivex.functions.Action

/**
 * Created by felip on 09/03/2018.
 */
interface LoadingContentView {
    fun showLoading(): Action

    fun hideLoading(): Action
}