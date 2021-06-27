package br.pedroso.tweetsentiment.features.tweetslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.features.tweetslist.usecases.AnalyseTweetSentiment
import br.pedroso.tweetsentiment.features.tweetslist.usecases.ClearCurrentUserSettings
import br.pedroso.tweetsentiment.features.tweetslist.usecases.GetCurrentUser
import br.pedroso.tweetsentiment.features.tweetslist.usecases.GetTweetsFromCurrentUser
import br.pedroso.tweetsentiment.features.tweetslist.usecases.SyncUserData
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TweetsListViewModel(
    private val syncUserData: SyncUserData,
    private val getCurrentUser: GetCurrentUser,
    private val getTweetsFromCurrentUser: GetTweetsFromCurrentUser,
    private val clearCurrentUserSettings: ClearCurrentUserSettings,
    private val analyseTweetSentiment: AnalyseTweetSentiment
) : ViewModel() {

    private val _singleEvents = LiveEvent<TweetsListSingleEvent>()
    val singleEvents: LiveData<TweetsListSingleEvent>
        get() = _singleEvents

    private val _viewState = MutableLiveData<TweetsListViewState>()
    val viewState: LiveData<TweetsListViewState>
        get() = _viewState

    private val _userState = MutableLiveData<User>()
    val userState: LiveData<User>
        get() = _userState

    private val _tweetsListState = MutableLiveData<List<Tweet>>()
    val tweetsListState: LiveData<List<Tweet>>
        get() = _tweetsListState

    init {
        initUserStateBinding()
        initTweetsListStateBinding()
        refreshData()
    }

    fun onViewEvent(viewEvent: TweetsListViewEvent) {
        when (viewEvent) {
            TweetsListViewEvent.RequestedRefresh -> refreshData()
            is TweetsListViewEvent.AnalyseTweet -> analyseTweet(viewEvent.tweet)
            TweetsListViewEvent.PressedBackButton -> handleBackButton()
            TweetsListViewEvent.RequestedToSelectOtherUser -> handleRequestedToSelectOtherUser()
            TweetsListViewEvent.SelectOtherUserConfirmed -> cleanCurrentUserAndCloseScreen()
        }
    }

    private fun initUserStateBinding() {
        viewModelScope.launch {
            getCurrentUser.execute().collect { user ->
                _userState.value = user
            }
        }
    }

    private fun initTweetsListStateBinding() {
        viewModelScope.launch {
            getTweetsFromCurrentUser.execute().collect { tweetsList ->
                _tweetsListState.value = tweetsList
            }
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            try {
                _viewState.value = TweetsListViewState.LoadingContent
                syncUserData.execute()
                _viewState.value = TweetsListViewState.Ready
            } catch (error: Throwable) {
                _viewState.value = TweetsListViewState.Error(error)
            }
        }
    }

    private fun analyseTweet(tweet: Tweet) {
        viewModelScope.launch {
            try {
                _viewState.value = TweetsListViewState.RunningAnalysis
                analyseTweetSentiment.execute(tweet)
            } catch (error: Throwable) {
                _singleEvents.value = TweetsListSingleEvent.DisplayErrorDuringAnalysisMessage
            } finally {
                _viewState.value = TweetsListViewState.Ready
            }
        }
    }

    private fun cleanCurrentUserAndCloseScreen() {
        clearCurrentUserSettings.execute()
        _singleEvents.value = TweetsListSingleEvent.CloseScreen
    }

    private fun handleRequestedToSelectOtherUser() {
        _singleEvents.value = TweetsListSingleEvent.DisplaySelectOtherUserConfirmation
    }

    private fun handleBackButton() {
        if (_viewState.value is TweetsListViewState.Error) {
            cleanCurrentUserAndCloseScreen()
        } else {
            _singleEvents.value = TweetsListSingleEvent.DisplaySelectOtherUserConfirmation
        }
    }
}
