package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetUserLatestTweetsFromApi(
    private val scheduler: Scheduler,
    private val twitterDataSource: TwitterDataSource
) {

    fun execute(user: User): Observable<Tweet> {
        return twitterDataSource.getLatestTweetsFromUser(user)
            .subscribeOn(scheduler)
    }
}
