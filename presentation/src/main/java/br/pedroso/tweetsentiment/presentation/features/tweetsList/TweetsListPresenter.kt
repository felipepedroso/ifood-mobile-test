package br.pedroso.tweetsentiment.presentation.features.tweetsList

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.FirstSyncBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.UserFlowableBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.UserTweetsFlowableBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.FirstSync
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserTweetsFlowableFromDatabase
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserFlowableFromDatabase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by felip on 10/03/2018.
 */
class TweetsListPresenter(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView,
        private val applicationPreferences: ApplicationPreferences,
        private val firstSync: FirstSync,
        private val firstSyncBehaviorCoordinator: FirstSyncBehaviorCoordinator,
        private val getUserFlowableFromDatabase: GetUserFlowableFromDatabase,
        private val userFlowableBehaviorCoordinator: UserFlowableBehaviorCoordinator,
        private val getUserTweetsFlowableFromDatabase: GetUserTweetsFlowableFromDatabase,
        private val userTweetsFlowableBehaviorCoordinator: UserTweetsFlowableBehaviorCoordinator) {

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun executeFirstSync() {
        val firstSyncSubscription = firstSync.execute()
                .compose(firstSyncBehaviorCoordinator)
                .observeOn(uiScheduler)
                .subscribe(
                        { completedFirstSync() },
                        { Timber.e(it) }
                )

        compositeDisposable.add(firstSyncSubscription)
    }

    private fun completedFirstSync() {
        Timber.d("Completed first sync.")
        loadUserData()
    }

    private fun loadUserData() {
        val user = getUserFlowableFromDatabase.execute()
                .doOnNext { Timber.d("Loaded user: $it") }
                .compose(userFlowableBehaviorCoordinator)

        val tweets = user
                .flatMap { getUserTweetsFlowableFromDatabase.execute(it) }
                .compose(userTweetsFlowableBehaviorCoordinator)
                .subscribe(
                        { Timber.d("Loaded tweet: $it") },
                        { Timber.e(it) }
                )

        compositeDisposable.add(tweets)
    }

    fun releaseSubscriptions() {
        compositeDisposable.clear()
    }

    fun clickedOnAnalyzeTweet(tweet: Tweet) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun clickedOnSelectOtherUser() {
        view.askConfirmationToSelectOtherUser()
    }

    fun confirmedToChangeUser() {
        applicationPreferences.cleanUsernameToSync()
        view.navigateToApplicationHome()
    }
}