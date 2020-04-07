package br.pedroso.tweetsentiment.presentation.features.home

import androidx.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import io.reactivex.Observable
import io.reactivex.Scheduler
import kotlinx.coroutines.rx2.rxObservable

class HomeViewModel(
    private val homeSyncUser: HomeSyncUser,
    private val uiScheduler: Scheduler
) : ViewModel() {

    fun syncNewUser(userName: String): Observable<ViewState> {
        return rxObservable {
            try {
                send(ViewState.Loading)
                homeSyncUser.execute(userName)
                send(ViewState.Success)
            } catch (exception: Exception) {
                send(ViewState.Error(exception))

            }
            close()
        }.observeOn(uiScheduler)
    }
}
