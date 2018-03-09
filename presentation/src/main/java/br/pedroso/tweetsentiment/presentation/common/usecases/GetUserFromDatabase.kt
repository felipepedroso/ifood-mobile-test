package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Maybe
import io.reactivex.Scheduler

/**
 * Created by felip on 09/03/2018.
 */
class GetUserFromDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(username: String): Maybe<User> {
        return databaseDataSource.getUser(username)
                .subscribeOn(scheduler)
    }
}