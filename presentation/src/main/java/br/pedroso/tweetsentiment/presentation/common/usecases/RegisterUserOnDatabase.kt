package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Completable
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class RegisterUserOnDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(user: User) {
        Completable.fromAction { databaseDataSource.registerUser(user) }
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .subscribe()
    }
}