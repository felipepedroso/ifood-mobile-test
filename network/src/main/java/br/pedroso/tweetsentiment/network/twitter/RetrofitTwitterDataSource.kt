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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitTwitterDataSource(
    private val dispatcher: CoroutineDispatcher,
    private val twitterAuthService: TwitterAuthService,
    private val twitterService: TwitterService,
    private val applicationSettings: ApplicationSettings
) : TwitterDataSource {

    override suspend fun getUser(userName: String): User {
        try {
            ensureAuthenticationBeforeApiCall()

            return withContext(dispatcher) {
                val retrofitUser = twitterService.usersShow(userName)
                UserMapper.mapRetrofitToDomain(retrofitUser)
            }
        } catch (exception: Exception) {
            throw mapTwitterError(exception)
        }
    }

    override suspend fun getTweetsSinceTweet(user: User, tweet: Tweet): List<Tweet> {
        try {
            ensureAuthenticationBeforeApiCall()

            return withContext(dispatcher) {
                val retrofitTweets = twitterService.statusesUserTimeline(user.userName, tweet.id)
                retrofitTweets.map { TweetMapper.mapTwitterApiToDomain(it) }
            }
        } catch (exception: Exception) {
            throw mapTwitterError(exception)
        }
    }

    override suspend fun getLatestTweetsFromUser(user: User): List<Tweet> {
        try {
            ensureAuthenticationBeforeApiCall()

            return withContext(dispatcher) {
                val retrofitTweets = twitterService.statusesUserTimeline(user.userName)
                retrofitTweets.map { TweetMapper.mapTwitterApiToDomain(it) }
            }
        } catch (exception: Exception) {
            throw mapTwitterError(exception)
        }
    }

    private suspend fun ensureAuthenticationBeforeApiCall(): String {
        return withContext(dispatcher) {
            if (applicationSettings.hasTwitterAccessToken()) {
                applicationSettings.retrieveTwitterAccessToken()
            } else {
                doAuthentication()
            }
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
