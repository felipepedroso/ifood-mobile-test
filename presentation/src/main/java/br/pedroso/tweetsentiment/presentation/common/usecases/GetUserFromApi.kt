package br.pedroso.tweetsentiment.presentation.shared.usecases

import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetUserFromApi(
        private val scheduler: Scheduler,
        private val twitterDataSource: TwitterDataSource) {

    fun execute(username: String): Observable<User> {
        return twitterDataSource.getUser(username)
                .subscribeOn(scheduler)
    }
}