package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import io.reactivex.Completable
import io.reactivex.Scheduler

class RegisterUserOnDatabase(
    private val scheduler: Scheduler,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(user: User) {
        Completable.fromAction { databaseDataSource.registerUser(user) }
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .subscribe()
    }
}
