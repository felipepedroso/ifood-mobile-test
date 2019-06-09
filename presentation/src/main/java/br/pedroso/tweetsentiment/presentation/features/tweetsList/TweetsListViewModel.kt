package br.pedroso.tweetsentiment.presentation.features.tweetsList

import androidx.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.*
import br.pedroso.tweetsentiment.presentation.features.tweetsList.viewStateTransformers.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler

class TweetsListViewModel(
        private val uiScheduler: Scheduler,
        private val syncUserData: SyncUserData,
        private val getCurrentUser: GetCurrentUser,
        private val getTweetsFromCurrentUser: GetTweetsFromCurrentUser,
        private val clearCurrentUserSettings: ClearCurrentUserSettings,
        private val analyseTweetSentiment: AnalyseTweetSentiment) : ViewModel() {

    fun analyzeTweet(tweet: Tweet): Observable<ViewState> {
        return analyseTweetSentiment
                .execute(tweet)
                .compose(AnalyseTweetsStateTransformer())
                .observeOn(uiScheduler)
    }

    fun syncUserData(): Completable {
        return syncUserData
                .execute()
                .observeOn(uiScheduler)
    }

    fun getCurrentUser() : Flowable<ViewState> {
        return getCurrentUser
                .execute()
                .compose(GetCurrentUserStateTransformer())
                .observeOn(uiScheduler)
    }

    fun getTweetsFromCurrentUser(): Flowable<ViewState> {
        return getTweetsFromCurrentUser
                .execute()
                .compose(GetTweetsFromCurrentUserStateTransformer())
                .observeOn(uiScheduler)
    }

    fun clearCurrentUserSettings(): Completable {
        return clearCurrentUserSettings
                .execute()
                .observeOn(uiScheduler)
    }
}