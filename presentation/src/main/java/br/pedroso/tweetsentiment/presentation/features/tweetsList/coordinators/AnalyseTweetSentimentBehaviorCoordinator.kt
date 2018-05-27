package br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.TweetsListErrorStatePresenter
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingPresenter
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class AnalyseTweetSentimentBehaviorCoordinator(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : ObservableTransformer<Sentiment, Sentiment> {
    override fun apply(upstream: Observable<Sentiment>): ObservableSource<Sentiment> {
        return upstream
                .compose(LoadingPresenter(uiScheduler, view))
                .compose(TweetsListErrorStatePresenter(uiScheduler, view))
    }
}