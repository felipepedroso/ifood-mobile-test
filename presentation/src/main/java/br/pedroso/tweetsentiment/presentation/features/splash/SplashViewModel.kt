package br.pedroso.tweetsentiment.presentation.features.splash

import android.arch.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.splash.usecases.SplashSyncRegisteredUser
import br.pedroso.tweetsentiment.presentation.features.splash.viewState.SplashViewStateTransformer
import io.reactivex.Observable
import io.reactivex.Scheduler

class SplashViewModel(
        private val splashSyncRegisteredUser: SplashSyncRegisteredUser,
        private val uiScheduler: Scheduler) : ViewModel() {

    fun syncRegisteredUser(): Observable<ViewState> {
        return splashSyncRegisteredUser.execute()
                .compose(SplashViewStateTransformer())
                .observeOn(uiScheduler)
    }
}