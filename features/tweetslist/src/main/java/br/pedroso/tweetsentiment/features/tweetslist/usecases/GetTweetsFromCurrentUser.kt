package br.pedroso.tweetsentiment.features.tweetslist.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Tweet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

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
