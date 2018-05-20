package br.pedroso.tweetsentiment.network.twitter

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.network.twitter.retrofit.mappers.TweetMapper
import br.pedroso.tweetsentiment.network.twitter.retrofit.mappers.UserMapper
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterAuthService
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterService
import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.HttpException

/**
 * Created by felip on 09/03/2018.
 */
class RetrofitTwitterDataSource(
        private val twitterAuthService: TwitterAuthService,
        private val twitterService: TwitterService,
        private val applicationSettings: ApplicationSettings) : TwitterDataSource {

    override fun getUser(userName: String): Observable<User> {
        return ensureAuthenticationBeforeApiCall()
                .flatMap { twitterService.usersShow(userName) }
                .map { UserMapper.mapRetrofitToDomain(it) }
                .onErrorResumeNext(TwitterApiErrorMapper())
    }

    override fun getTweetsSinceTweet(user: User, tweet: Tweet): Observable<Tweet> {
        return ensureAuthenticationBeforeApiCall()
                .flatMap { twitterService.statusesUserTimeline(user.userName, tweet.id) }
                .flatMap { Observable.fromIterable(it) }
                .map { TweetMapper.mapTwitterApiToDomain(it) }
                .onErrorResumeNext(TwitterApiErrorMapper())
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
    }

    private class TwitterApiErrorMapper<T> : Function<Throwable, Observable<T>> {
        override fun apply(error: Throwable): Observable<T> {
            if (error is HttpException) {
                return when (error.code()) {
                    404 -> Observable.error(TwitterError.UserNotFound())
                    else -> Observable.error(TwitterError.UndesiredResponse())
                }
            } else if (error is TwitterError) {
                return Observable.error(error)
            }

            return Observable.error(TwitterError.UndesiredResponse())
        }
    }
}