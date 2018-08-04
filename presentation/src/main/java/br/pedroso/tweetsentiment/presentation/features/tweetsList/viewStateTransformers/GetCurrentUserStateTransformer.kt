package br.pedroso.tweetsentiment.presentation.features.tweetsList.viewStateTransformers

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.entities.ViewState
import io.reactivex.*
import org.reactivestreams.Publisher

class GetCurrentUserStateTransformer : FlowableTransformer<User, ViewState> {
    override fun apply(upstream: Flowable<User>): Publisher<ViewState> {
        return upstream
                .map { ViewState.ShowContent(it) as ViewState }
                .defaultIfEmpty(ViewState.Empty())
                .onErrorReturn { ViewState.Error(it) }
                .startWith(ViewState.Loading())
    }
}