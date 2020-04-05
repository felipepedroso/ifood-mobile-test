package br.pedroso.tweetsentiment.network.twitter

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.network.twitter.retrofit.mappers.TweetMapper
import br.pedroso.tweetsentiment.network.twitter.retrofit.mappers.UserMapper
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterAuthService
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.Function
import kotlinx.coroutines.rx2.asCoroutineDispatcher
import kotlinx.coroutines.rx2.rxObservable
import retrofit2.HttpException

class RetrofitTwitterDataSource(
    private val workerScheduler: Scheduler,
    private val twitterAuthService: TwitterAuthService,
    private val twitterService: TwitterService,
    private val applicationSettings: ApplicationSettings
) : TwitterDataSource {

    override fun getUser(userName: String): Observable<User> {
        return rxObservable(workerScheduler.asCoroutineDispatcher()) {
            try {
                ensureAuthenticationBeforeApiCall()

                val retrofitUser = twitterService.usersShow(userName)

                val user = UserMapper.mapRetrofitToDomain(retrofitUser)
                send(user)
                close()
            } catch (exception: Exception) {
                val error = mapTwitterError(exception)
                close(error)
            }
        }
    }

    override fun getTweetsSinceTweet(user: User, tweet: Tweet): Observable<Tweet> {
        return rxObservable(workerScheduler.asCoroutineDispatcher()) {
            try {
                ensureAuthenticationBeforeApiCall()

                val retrofitTweets = twitterService.statusesUserTimeline(user.userName, tweet.id)

                if (retrofitTweets.isNotEmpty()) {
                    retrofitTweets
                        .map { TweetMapper.mapTwitterApiToDomain(it) }
                        .forEach { tweet -> send(tweet) }

                    close()
                } else {
                    close(TwitterError.EmptyResponse())
                }
            } catch (exception: Exception) {
                val error = mapTwitterError(exception)
                close(error)
            }
        }
    }

    override fun getLatestTweetsFromUser(user: User): Observable<Tweet> {
        return rxObservable(workerScheduler.asCoroutineDispatcher()) {
            try {
                ensureAuthenticationBeforeApiCall()

                val retrofitTweets = twitterService.statusesUserTimeline(user.userName)

                if (retrofitTweets.isNotEmpty()) {
                    retrofitTweets
                        .map { TweetMapper.mapTwitterApiToDomain(it) }
                        .forEach { tweet -> send(tweet) }

                    close()
                } else {
                    close(TwitterError.EmptyResponse())
                }
            } catch (exception: Exception) {
                val error = mapTwitterError(exception)
                close(error)
            }
        }
    }

    private suspend fun ensureAuthenticationBeforeApiCall(): String {
        return if (applicationSettings.hasTwitterAccessToken()) {
            applicationSettings.retrieveTwitterAccessToken()
        } else {
            doAuthentication()
        }
    }

    private suspend fun doAuthentication(): String {
        try {
            val authenticationResult = twitterAuthService.applicationOnlyAuthentication()

            if (authenticationResult.tokenType == "bearer") {
                val token = authenticationResult.accessToken

                applicationSettings.storeTwitterAccessToken(token)

                return token
            } else {
                throw TwitterError.AuthenticationError()
            }
        } catch (exception: Exception) {
            throw TwitterError.AuthenticationError()
        }
    }

    private fun mapTwitterError(exception: Exception): Throwable = when {
        exception is HttpException && exception.code() == NOT_FOUND_ERROR_CODE -> {
            TwitterError.UserNotFound()
        }
        exception is TwitterError -> exception
        else -> TwitterError.UndesiredResponse()
    }

    companion object {
        private const val NOT_FOUND_ERROR_CODE = 404
    }
}
