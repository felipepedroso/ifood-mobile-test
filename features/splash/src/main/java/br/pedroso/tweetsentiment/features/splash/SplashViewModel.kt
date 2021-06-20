package br.pedroso.tweetsentiment.features.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pedroso.tweetsentiment.features.splash.SplashSingleEvent.NavigateToHomeScreen
import br.pedroso.tweetsentiment.features.splash.SplashSingleEvent.NavigateToTweetsListScreen
import br.pedroso.tweetsentiment.features.splash.usecases.SplashSyncRegisteredUser
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashSyncRegisteredUser: SplashSyncRegisteredUser
) : ViewModel() {

    private val _singleEvents = LiveEvent<SplashSingleEvent>()
    val singleEvents: LiveData<SplashSingleEvent>
        get() = _singleEvents

    init {
        viewModelScope.launch {
            val event = try {
                val registeredUser = splashSyncRegisteredUser.execute()

                if (registeredUser == null) {
                    NavigateToHomeScreen
                } else {
                    NavigateToTweetsListScreen
                }
            } catch (exception: Exception) {
                NavigateToHomeScreen
            }

            _singleEvents.postValue(event)
        }
    }
}
