package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.utils.Result
import io.reactivex.Maybe
import io.reactivex.Scheduler

/**
 * Created by felip on 11/03/2018.
 */
class GetUserRecordOnDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(username: String): Maybe<Result> {
        return databaseDataSource.getUserRecordOnDatabase(username)
                .subscribeOn(scheduler)
                .map { Result.WithValue(it) as Result }
                .switchIfEmpty(Maybe.just(Result.Empty() as Result))
    }
}