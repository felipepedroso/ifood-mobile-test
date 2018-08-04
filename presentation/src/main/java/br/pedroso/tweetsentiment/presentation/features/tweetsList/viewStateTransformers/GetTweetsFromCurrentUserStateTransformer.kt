package br.pedroso.tweetsentiment.presentation.features.tweetsList.viewStateTransformers

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.entities.ViewState
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher

class GetTweetsFromCurrentUserStateTransformer : FlowableTransformer<List<Tweet>, ViewState> {
    override fun apply(upstream: Flowable<List<Tweet>>): Publisher<ViewState> {
        return upstream
                .map { ViewState.ShowContent(it) as ViewState }
                .defaultIfEmpty(ViewState.Empty())
                .onErrorReturn { ViewState.Error(it) }
                .startWith(ViewState.Loading())
    }
}