package br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.shared.behaviors.Behavior
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

/**
 * Created by felip on 10/03/2018.
 */
class ShowUserProfileBehavior(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : FlowableTransformer<User, User>, Behavior(uiScheduler) {
    override fun apply(upstream: Flowable<User>): Publisher<User> {
        return upstream
                .observeOn(uiScheduler)
                .doOnNext { subscribeAndFireAction(view.showUserProfile(it)) }

    }
}