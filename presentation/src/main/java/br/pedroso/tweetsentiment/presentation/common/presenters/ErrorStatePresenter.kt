package br.pedroso.tweetsentiment.presentation.common.presenters

import br.pedroso.tweetsentiment.presentation.shared.behaviors.Presenter
import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * Created by felip on 10/03/2018.
 */
abstract class ErrorStatePresenter<T>(private val uiScheduler: Scheduler)
    : CompletableTransformer, FlowableTransformer<T, T>, ObservableTransformer<T, T>, Presenter(uiScheduler) {

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