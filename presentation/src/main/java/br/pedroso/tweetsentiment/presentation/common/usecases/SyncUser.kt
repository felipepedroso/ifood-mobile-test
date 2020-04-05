package br.pedroso.tweetsentiment.presentation.common.usecases

import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.domain.utils.Optional
import io.reactivex.Maybe
import io.reactivex.Observable

class SyncUser(
    private val twitterDataSource: TwitterDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    fun execute(username: String): Observable<User> {
        return getUserOnDatabase(username)
            .flatMapObservable { optional ->
                val user = optional.value

                if (user != null) {
                    Observable.just(user)
                } else {
                    twitterDataSource.getUser(username)
                        .flatMap { user ->
                            databaseDataSource.registerUser(user).andThen(Observable.just(user))
                        }
                }
            }
    }

    private fun getUserOnDatabase(username: String): Maybe<Optional<User>> {
        return databaseDataSource.getUserRecordOnDatabase(username)
            .map { Optional.of(it) }
            .defaultIfEmpty(Optional.empty())
    }
}
