package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterRepository

class SyncUser(
    private val twitterRepository: TwitterRepository,
    private val databaseDataSource: DatabaseDataSource
) {

    suspend fun execute(username: String): User {
        return databaseDataSource.getUserRecordOnDatabase(username)
            ?: twitterRepository.getUser(username).also { databaseDataSource.registerUser(it) }
    }
}
