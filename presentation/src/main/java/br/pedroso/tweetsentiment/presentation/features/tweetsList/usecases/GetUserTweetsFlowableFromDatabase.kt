package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

/**
 * Created by felip on 10/03/2018.
 */
class GetUserTweetsFlowableFromDatabase(
        private val scheduler: Scheduler,
        private val databaseDataSource: DatabaseDataSource) {

    fun execute(user: User): Flowable<Tweet> {
        return databaseDataSource.getTweetsFromUser(user)
                .subscribeOn(scheduler)
    }
}