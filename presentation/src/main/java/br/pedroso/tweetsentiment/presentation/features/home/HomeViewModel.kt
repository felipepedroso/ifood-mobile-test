package br.pedroso.tweetsentiment.presentation.features.home

import androidx.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import br.pedroso.tweetsentiment.presentation.features.home.viewState.HomeViewStateTransformer
import io.reactivex.Observable
import io.reactivex.Scheduler

class HomeViewModel(
    private val homeSyncUser: HomeSyncUser,
    private val uiScheduler: Scheduler
) : ViewModel() {

    fun syncNewUser(userName: String): Observable<ViewState> {
        return homeSyncUser.execute(userName)
            .compose(HomeViewStateTransformer())
            .observeOn(uiScheduler)
    }
}
