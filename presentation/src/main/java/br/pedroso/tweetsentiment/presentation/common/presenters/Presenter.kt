package br.pedroso.tweetsentiment.presentation.shared.behaviors

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.functions.Action

/**
 * Created by felip on 09/03/2018.
 */
abstract class Presenter(private val scheduler: Scheduler) {

    protected fun subscribeAndFireAction(actionToPerform: Action) {
        Completable.fromAction(actionToPerform)
                .subscribeOn(scheduler)
                .subscribe()

    }
}