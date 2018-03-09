package br.pedroso.tweetsentiment.presentation.shared.behaviors.emptyState

import io.reactivex.functions.Action

/**
 * Created by felip on 09/03/2018.
 */
interface EmptyStateView {
    fun showEmptyState(): Action

    fun hideEmptyState(): Action
}