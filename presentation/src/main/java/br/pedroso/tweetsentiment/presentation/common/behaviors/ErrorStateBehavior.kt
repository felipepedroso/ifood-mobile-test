package br.pedroso.tweetsentiment.presentation.common.behaviors

import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * Created by felip on 10/03/2018.
 */
abstract class ErrorStateBehavior<T>(private val uiScheduler: Scheduler)
    : CompletableTransformer, FlowableTransformer<T, T>, ObservableTransformer<T, T>, Behavior(uiScheduler) {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { executeHideErrorStateActions() }
                .doOnError { executeShowErrorStateActions(it) }
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { executeHideErrorStateActions() }
                .doOnError { executeShowErrorStateActions(it) }
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { executeHideErrorStateActions() }
                .doOnError { executeShowErrorStateActions(it) }
    }

    abstract fun executeShowErrorStateActions(error: Throwable)

    abstract fun executeHideErrorStateActions()
}