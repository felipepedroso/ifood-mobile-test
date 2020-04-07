package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Tweet
import io.reactivex.Flowable
import io.reactivex.Scheduler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.rx2.asFlowable

class GetTweetsFromCurrentUser(
    private val getCurrentUser: GetCurrentUser,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(): Flow<List<Tweet>> {
        return getCurrentUser
            .execute()
            .flatMapConcat { databaseDataSource.getTweetsFromUser(it) }
    }
}
