package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.utils.Result
import io.reactivex.Maybe
import io.reactivex.Scheduler

class GetUserLatestTweetOnDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(user: User): Maybe<Result> {
        return databaseDataSource.getLatestTweetFromUser(user)
                .subscribeOn(scheduler)
                .map { Result.WithValue(it) as Result }
                .switchIfEmpty(Maybe.just(Result.Empty() as Result))
    }
}