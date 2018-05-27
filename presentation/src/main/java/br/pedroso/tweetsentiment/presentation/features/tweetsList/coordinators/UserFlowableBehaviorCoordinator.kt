package br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.ShowUserProfilePresenter
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.TweetsListErrorStatePresenter
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingPresenter
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import org.reactivestreams.Publisher

/**
 * Created by felip on 10/03/2018.
 */
class UserFlowableBehaviorCoordinator(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : FlowableTransformer<User, User> {
    override fun apply(upstream: Flowable<User>): Publisher<User> {
        return upstream
                .compose(LoadingPresenter(uiScheduler, view))
                .compose(ShowUserProfilePresenter(uiScheduler, view))
                .compose(TweetsListErrorStatePresenter(uiScheduler, view))
    }
}