package br.pedroso.tweetsentiment.presentation.features.home.usecases

import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.presentation.common.usecases.SyncUser
import io.reactivex.Observable

class HomeSyncUser(
    private val validateUsername: ValidateUsername,
    private val syncUser: SyncUser,
    private val storeUserToSyncOnPreferences: StoreUserToSyncOnPreferences
) {

    fun execute(username: String): Observable<User> {
        return validateUsername.execute(username)
            .flatMap { syncUser.execute(it) }
            .flatMap { user ->
                storeUserToSyncOnPreferences.execute(user.userName).andThen(Observable.just(user))
            }
    }
}
