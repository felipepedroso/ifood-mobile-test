package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.utils.Result
import io.reactivex.Observable
import io.reactivex.Scheduler

class SyncUser(
        private val scheduler: Scheduler,
        private val getUserFromApi: GetUserFromApi,
        private val registerUserOnDatabase: RegisterUserOnDatabase,
        private val getUserRecordOnDatabase: GetUserRecordOnDatabase) {

    fun execute(username: String): Observable<User> {
        return getUserFromApi.execute(username)
                .subscribeOn(scheduler)
                .doOnNext { registerOrUpdateUserOnDatabase(it) }
    }

    private fun registerOrUpdateUserOnDatabase(user: User) {
        getUserRecordOnDatabase.execute(user.userName)
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe {
                    when (it) {
                        is Result.WithValue<*> -> updateUserOnDatabase(it.value as User, user)
                        else -> registerUserOnDatabase.execute(user)
                    }
                }
    }

    private fun updateUserOnDatabase(userFromDatabase: User, userFromApi: User) {
        if (userFromDatabase != userFromApi) {
            registerUserOnDatabase.execute(userFromApi)
        }
    }
}