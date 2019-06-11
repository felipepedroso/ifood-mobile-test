package br.pedroso.tweetsentiment.presentation.features.tweetsList.usecases

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.utils.Result
import io.reactivex.ObservableSource
import io.reactivex.Scheduler

class SyncUserTweets(
    private val scheduler: Scheduler,
    private val getUserLatestTweetOnDatabase: GetUserLatestTweetOnDatabase,
    private val getUserTweetsSinceTweet: GetUserTweetsSinceTweet,
    private val getUserLatestTweetsFromApi: GetUserLatestTweetsFromApi,
    private val registerTweetOnDatabase: RegisterTweetOnDatabase
) {

    fun execute(user: User): ObservableSource<Tweet> {
        return getUserLatestTweetOnDatabase.execute(user)
            .flatMapObservable {
                when (it) {
                    is Result.WithValue<*> -> getUserTweetsSinceTweet.execute(
                        user,
                        it.value as Tweet
                    )
                    else -> getUserLatestTweetsFromApi.execute(user)
                }
            }
            .doOnNext { registerTweetOnDatabase.execute(user, it) }
            .subscribeOn(scheduler)
    }
}
