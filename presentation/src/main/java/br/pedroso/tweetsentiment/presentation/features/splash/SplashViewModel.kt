package br.pedroso.tweetsentiment.presentation.features.splash

import androidx.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.splash.usecases.SplashSyncRegisteredUser
import io.reactivex.Observable
import io.reactivex.Scheduler
import kotlinx.coroutines.rx2.rxObservable

class SplashViewModel(
    private val splashSyncRegisteredUser: SplashSyncRegisteredUser,
    private val uiScheduler: Scheduler
) : ViewModel() {

    fun syncRegisteredUser(): Observable<ViewState> {
        return rxObservable {
            try {
                send(ViewState.Loading)
                val registeredUser = splashSyncRegisteredUser.execute()

                if (registeredUser != null) {
                    send(ViewState.ShowContent(registeredUser))
                } else {
                    send(ViewState.Empty)
                }
            } catch (exception: Exception) {
                send(ViewState.Error(exception))
            }
            close()
        }.observeOn(uiScheduler)
    }
}
