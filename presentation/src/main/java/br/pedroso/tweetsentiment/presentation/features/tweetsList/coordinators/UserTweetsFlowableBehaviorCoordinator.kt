package br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.ShowUserTweetBehavior
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.TweetsListErrorStateBehavior
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingBehavior
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

/**
 * Created by felip on 10/03/2018.
 */
class UserTweetsFlowableBehaviorCoordinator(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : FlowableTransformer<Tweet, Tweet> {
    override fun apply(upstream: Flowable<Tweet>): Publisher<Tweet> {
        return upstream
                .compose(LoadingBehavior(uiScheduler, view))
                .compose(ShowUserTweetBehavior(uiScheduler, view))
                .compose(TweetsListErrorStateBehavior(uiScheduler, view))
    }
}