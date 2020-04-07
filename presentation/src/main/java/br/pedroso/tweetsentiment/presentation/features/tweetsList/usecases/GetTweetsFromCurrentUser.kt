package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Tweet
import io.reactivex.Flowable
import io.reactivex.Scheduler
import kotlinx.coroutines.rx2.asFlowable

class GetTweetsFromCurrentUser(
    private val getCurrentUser: GetCurrentUser,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(): Flowable<List<Tweet>> {
        return getCurrentUser
            .execute()
            .flatMap { databaseDataSource.getTweetsFromUser(it).asFlowable() }
    }
}
