package br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.DisplaySentimentAnalysisBehavior
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.TweetsListErrorStateBehavior
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingBehavior
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
                .compose(LoadingBehavior(uiScheduler, view))
                .compose(TweetsListErrorStateBehavior(uiScheduler, view))
                .compose(DisplaySentimentAnalysisBehavior(uiScheduler, view))
    }
}