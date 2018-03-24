package br.pedroso.tweetsentiment.presentation.features.tweetsList

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.device.backgroundSync.BackgroundSyncScheduler
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.AnalyseTweetSentimentBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.FirstSyncBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.UserFlowableBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.coordinators.UserTweetsFlowableBehaviorCoordinator
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.FirstSync
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserFlowableFromDatabase
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetUserTweetsFlowableFromDatabase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by felip on 10/03/2018.
 */
class TweetsListPresenter(
        private val uiScheduler: Scheduler,
        private val view: TweetsListView,
        private val applicationSettings: ApplicationSettings,
        private val firstSync: FirstSync,
        private val firstSyncBehaviorCoordinator: FirstSyncBehaviorCoordinator,
        private val getUserFlowableFromDatabase: GetUserFlowableFromDatabase,
        private val userFlowableBehaviorCoordinator: UserFlowableBehaviorCoordinator,
        private val getUserTweetsFlowableFromDatabase: GetUserTweetsFlowableFromDatabase,
        private val userTweetsFlowableBehaviorCoordinator: UserTweetsFlowableBehaviorCoordinator,
        private val analyseTweetSentiment: AnalyseTweetSentiment,
        private val analyseTweetSentimentBehaviorCoordinator: AnalyseTweetSentimentBehaviorCoordinator,
        private val backgroundSyncScheduler: BackgroundSyncScheduler) {

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
        scheduleRecurrentSync()
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
        val subscription = analyseTweetSentiment.execute(tweet)
                .compose(analyseTweetSentimentBehaviorCoordinator)
                .observeOn(uiScheduler)
                .subscribe(
                        { Timber.d("Analysed sentiment: ${it.name}") },
                        { Timber.e(it) }
                )

        compositeDisposable.add(subscription)
    }

    fun clickedOnSelectOtherUser() {
        view.askConfirmationToSelectOtherUser()
    }

    fun confirmedToChangeUser() {
        applicationSettings.cleanUsernameToSync()
        backgroundSyncScheduler.stopAllJobs()
        view.navigateToApplicationHome()
    }

    private fun scheduleRecurrentSync() {
        val timeInterval = applicationSettings.retrieveRecurrentSyncTimeInterval()
        backgroundSyncScheduler.scheduleRecurringSync(timeInterval)
    }
}