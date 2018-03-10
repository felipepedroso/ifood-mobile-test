package br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class DisplaySentimentAnalysisBehavior(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : ObservableTransformer<Sentiment, Sentiment>, Behavior(uiScheduler) {
    override fun apply(upstream: Observable<Sentiment>): ObservableSource<Sentiment> {
        return upstream
                .observeOn(uiScheduler)
                .doOnNext { subscribeAndFireAction(view.displaySentimentAnalysisResult(it)) }
    }
}