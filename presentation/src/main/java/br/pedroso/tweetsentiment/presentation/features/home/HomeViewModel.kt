package br.pedroso.tweetsentiment.presentation.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.tweetsentiment.presentation.features.home.HomeSingleEvent.NavigateToTweetsListScreen
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewEvent.ChangedUsername
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewEvent.SubmitTwitterUsername
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewState.Error
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewState.Idle
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewState.Loading
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeSyncUser: HomeSyncUser
) : ViewModel() {

    private val _singleEvents = LiveEvent<HomeSingleEvent>()
    val singleEvents: LiveData<HomeSingleEvent>
        get() = _singleEvents

    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    fun onViewEvent(viewEvent: HomeViewEvent) {
        when (viewEvent) {
            is SubmitTwitterUsername -> executeSyncUser(viewEvent.username)
            is ChangedUsername -> displayIdleState(viewEvent.username)
        }
    }

    private fun executeSyncUser(username: String) {
        viewModelScope.launch {
            _viewState.value = Loading
            try {
                homeSyncUser.execute(username)
                displayIdleState(username)
                _singleEvents.postValue(NavigateToTweetsListScreen)
            } catch (error: Throwable) {
                _viewState.value = Error(error)
            }
        }
    }

    private fun displayIdleState(username: String) {
        _viewState.value = Idle(
            isContinueButtonEnabled = username.isNotBlank(),
            shouldDisplayEmptyUsernameError = username.isBlank()
        )
    }
}
