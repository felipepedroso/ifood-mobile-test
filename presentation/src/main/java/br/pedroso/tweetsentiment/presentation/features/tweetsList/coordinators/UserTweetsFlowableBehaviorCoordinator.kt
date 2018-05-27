package br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.ShowUserTweetPresenter
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.TweetsListErrorStatePresenter
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingPresenter
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
                .compose(LoadingPresenter(uiScheduler, view))
                .compose(ShowUserTweetPresenter(uiScheduler, view))
                .compose(TweetsListErrorStatePresenter(uiScheduler, view))
    }
}