package br.pedroso.tweetsentiment.presentation.shared.behaviors.loading

import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * Created by felip on 09/03/2018.
 */
class LoadingBehavior<T>(
        val uiScheduler: Scheduler,
        val view: LoadingContentView) : FlowableTransformer<T, T>, ObservableTransformer<T, T>, CompletableTransformer, Behavior(uiScheduler) {
    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { showLoading() }
                .doOnNext { hideLoading() }
                .doOnTerminate { hideLoading() }
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { showLoading() }
                .doOnTerminate { hideLoading() }
    }

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .subscribeOn(uiScheduler)
                .doOnSubscribe { showLoading() }
                .doOnNext { hideLoading() }
                .doOnTerminate { hideLoading() }
    }

    private fun hideLoading() {
        subscribeAndFireAction(view.hideLoading())
    }

    private fun showLoading() {
        subscribeAndFireAction(view.showLoading())
    }
}