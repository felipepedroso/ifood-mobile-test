package br.pedroso.tweetsentiment.presentation.features.home.usecases

import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.presentation.common.usecases.StoreUserToSyncOnPreferences
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import io.reactivex.Observable
import io.reactivex.Scheduler

class HomeSyncUser(
    private val scheduler: Scheduler,
    private val validateUsername: ValidateUsername,
    private val syncUser: SyncUser,
    private val storeUserToSyncOnPreferences: StoreUserToSyncOnPreferences
) {

    fun execute(username: String): Observable<User> {
        return validateUsername.execute(username)
            .flatMap { syncUser.execute(it) }
            .doOnNext { storeUserToSyncOnPreferences.execute(it.userName) }
            .subscribeOn(scheduler)
    }
}
