package br.pedroso.tweetsentiment.presentation.features.splash.viewState

import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.entities.ViewState
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class SplashViewStateTransformer : ObservableTransformer<User, ViewState> {
    override fun apply(upstream: Observable<User>): ObservableSource<ViewState> {
        return upstream
            .map { ViewState.ShowContent(it) as ViewState }
            .defaultIfEmpty(ViewState.Empty)
            .onErrorReturn { ViewState.Error(it) }
            .startWith(ViewState.Loading)
            .concatWith(Observable.just(ViewState.Done))
    }
}
