package br.pedroso.tweetsentiment.presentation.features.home.viewState

import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.entities.ViewState
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class HomeViewStateTransformer : ObservableTransformer<User, ViewState> {
    override fun apply(upstream: Observable<User>): ObservableSource<ViewState> {
        return upstream
                .map { ViewState.Success as ViewState }
                .defaultIfEmpty(ViewState.Empty)
                .onErrorReturn { ViewState.Error(it) }
                .startWith(ViewState.Loading)
    }
}