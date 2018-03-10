package br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators

import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import br.pedroso.tweetsentiment.presentation.features.tweetsList.behaviors.TweetsListErrorStateBehavior
import br.pedroso.tweetsentiment.presentation.shared.behaviors.loading.LoadingBehavior
import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class FirstSyncBehaviorCoordinator(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView) : CompletableTransformer {
    override fun apply(upstream: Completable): CompletableSource {
        return upstream
                .compose(LoadingBehavior<Any>(uiScheduler, view))
                .compose(TweetsListErrorStateBehavior<Any>(uiScheduler, view))
    }
}