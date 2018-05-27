package br.pedroso.tweetsentiment.presentation.features.home

import android.arch.lifecycle.ViewModel
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncUser
import br.pedroso.tweetsentiment.presentation.features.home.usecases.HomeSyncRegisteredUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler

class HomeViewModel(
        private val homeSyncUser: HomeSyncUser,
        private val homeSyncRegisteredUser: HomeSyncRegisteredUser,
        private val uiScheduler: Scheduler) : ViewModel() {

    fun syncNewUser(userName: String): Completable {
        return homeSyncUser.execute(userName)
                .observeOn(uiScheduler)
                .ignoreElements()
    }

    fun syncRegisteredUser(): Observable<User> {
        return homeSyncRegisteredUser.execute()
                .observeOn(uiScheduler)
    }
}