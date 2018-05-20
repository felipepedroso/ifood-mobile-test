package br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

/**
 * Created by felip on 10/03/2018.
 */
class ShowUserTweetBehavior(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : FlowableTransformer<Tweet, Tweet>, Behavior(uiScheduler) {
    override fun apply(upstream: Flowable<Tweet>): Publisher<Tweet> {
        return upstream
                .observeOn(uiScheduler)
                .doOnNext { subscribeAndFireAction(view.showTweet(it)) }

    }
}