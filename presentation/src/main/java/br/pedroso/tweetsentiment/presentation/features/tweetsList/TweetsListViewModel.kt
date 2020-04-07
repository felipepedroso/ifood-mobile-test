package br.pedroso.tweetsentiment.presentation.features.tweetsList

import androidx.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.ClearCurrentUserSettings
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetCurrentUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.GetTweetsFromCurrentUser
import br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases.SyncUserData
import br.pedroso.tweetsentiment.presentation.features.tweetsList.viewStateTransformers.GetCurrentUserStateTransformer
import br.pedroso.tweetsentiment.presentation.features.tweetsList.viewStateTransformers.GetTweetsFromCurrentUserStateTransformer
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import kotlinx.coroutines.rx2.asFlowable
import kotlinx.coroutines.rx2.rxCompletable
import kotlinx.coroutines.rx2.rxObservable

class TweetsListViewModel(
    private val uiScheduler: Scheduler,
    private val syncUserData: SyncUserData,
    private val getCurrentUser: GetCurrentUser,
    private val getTweetsFromCurrentUser: GetTweetsFromCurrentUser,
    private val clearCurrentUserSettings: ClearCurrentUserSettings,
    private val analyseTweetSentiment: AnalyseTweetSentiment
) : ViewModel() {

    fun analyzeTweet(tweet: Tweet): Observable<ViewState> {
        return rxObservable {
            try {
                send(ViewState.Loading)

                analyseTweetSentiment.execute(tweet)

                send(ViewState.Success)
            } catch (exception: Exception) {
                send(ViewState.Error(exception))
            }
            close()
        }.observeOn(uiScheduler)
    }

    fun syncUserData(): Completable {
        return rxCompletable {
            syncUserData.execute()
        }.observeOn(uiScheduler)
    }

    fun getCurrentUser(): Flowable<ViewState> {
        return getCurrentUser
            .execute()
            .asFlowable()
            .compose(GetCurrentUserStateTransformer())
            .observeOn(uiScheduler)
    }

    fun getTweetsFromCurrentUser(): Flowable<ViewState> {
        return getTweetsFromCurrentUser
            .execute()
            .asFlowable()
            .compose(GetTweetsFromCurrentUserStateTransformer())
            .observeOn(uiScheduler)
    }

    fun clearCurrentUserSettings(): Completable {
        return Completable.fromAction { clearCurrentUserSettings.execute() }
            .observeOn(uiScheduler)
    }
}
