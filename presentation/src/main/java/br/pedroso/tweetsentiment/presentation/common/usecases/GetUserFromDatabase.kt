package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetUserFromDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(username: String): Flowable<User> {
        return databaseDataSource.getUser(username)
                .subscribeOn(scheduler)
    }
}