package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import io.reactivex.Observable
import kotlinx.coroutines.rx2.rxObservable

class SyncUser(
    private val twitterDataSource: TwitterDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(username: String): Observable<User> {
        return rxObservable {
            try {
                val userFromDatabase = databaseDataSource.getUserRecordOnDatabase(username)

                if (userFromDatabase != null) {
                    send(userFromDatabase)
                    close()
                } else {
                    val userFromNetwork = twitterDataSource.getUser(username)

                    databaseDataSource.registerUser(userFromNetwork)

                    send(userFromNetwork)
                    close()
                }
            } catch (exception: Exception) {
                close(exception)
            }
        }
    }
}
