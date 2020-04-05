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
import retrofit2.HttpException

class RetrofitTwitterDataSource(
    private val workerScheduler: Scheduler,
    private val twitterAuthService: TwitterAuthService,
    private val twitterService: TwitterService,
    private val applicationSettings: ApplicationSettings
) : TwitterDataSource {

    override fun getUser(userName: String): Observable<User> {
        return ensureAuthenticationBeforeApiCall()
            .flatMap { twitterService.usersShow(userName) }
            .map { UserMapper.mapRetrofitToDomain(it) }
            .onErrorResumeNext(TwitterApiErrorMapper())
            .subscribeOn(workerScheduler)
    }

    override fun getTweetsSinceTweet(user: User, tweet: Tweet): Observable<Tweet> {
        return ensureAuthenticationBeforeApiCall()
            .flatMap { twitterService.statusesUserTimeline(user.userName, tweet.id) }
            .flatMap { Observable.fromIterable(it) }
            .map { TweetMapper.mapTwitterApiToDomain(it) }
            .onErrorResumeNext(TwitterApiErrorMapper())
            .subscribeOn(workerScheduler)
    }

    override fun getLatestTweetsFromUser(user: User): Observable<Tweet> {
        return ensureAuthenticationBeforeApiCall()
            .flatMap { twitterService.statusesUserTimeline(user.userName) }
            .flatMap {
                when (it.isEmpty()) {
                    true -> Observable.error(TwitterError.EmptyResponse())
                    else -> Observable.fromIterable(it)
                }
            }
            .map { TweetMapper.mapTwitterApiToDomain(it) }
            .onErrorResumeNext(TwitterApiErrorMapper())
            .subscribeOn(workerScheduler)
    }

    private fun ensureAuthenticationBeforeApiCall(): Observable<String> {
        return if (applicationSettings.hasTwitterAccessToken())
            Observable.just(applicationSettings.retrieveTwitterAccessToken())
        else
            doAuthentication()
    }

    private fun doAuthentication(): Observable<String> {
        return twitterAuthService.applicationOnlyAuthentication()
            .flatMap {
                when (it.tokenType) {
                    "bearer" -> Observable.just(it.accessToken)
                    else -> Observable.error(TwitterError.AuthenticationError())
                }
            }
            .doOnNext { applicationSettings.storeTwitterAccessToken(it) }
            .onErrorResumeNext(Observable.error(TwitterError.AuthenticationError()))
            .subscribeOn(workerScheduler)
    }

    private class TwitterApiErrorMapper<T> : Function<Throwable, Observable<T>> {
        override fun apply(error: Throwable): Observable<T> {
            val error = when {
                error is HttpException && error.code() == NOT_FOUND_ERROR_CODE -> {
                    TwitterError.UserNotFound()
                }
                error is TwitterError -> error
                else -> TwitterError.UndesiredResponse()
            }

            return Observable.error(error)
        }
    }

    companion object {
        private const val NOT_FOUND_ERROR_CODE = 404
    }
}
