package br.pedroso.tweetsentiment.presentation.features.tweetsList.viewStateTransformers

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.ViewState
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class AnalyseTweetsStateTransformer : ObservableTransformer<Sentiment, ViewState> {
    override fun apply(upstream: Observable<Sentiment>): ObservableSource<ViewState> {
        return upstream
                .map { ViewState.Success as ViewState }
                .onErrorReturn { ViewState.Error(it) }
                .startWith(ViewState.Loading)
    }

}