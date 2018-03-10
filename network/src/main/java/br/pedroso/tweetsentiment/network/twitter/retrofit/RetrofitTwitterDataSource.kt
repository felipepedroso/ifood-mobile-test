package br.pedroso.tweetsentiment.network.twitter.retrofit

import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.network.twitter.retrofit.mappers.TweetMapper
import br.pedroso.tweetsentiment.network.twitter.retrofit.mappers.UserMapper
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterAuthService
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterService
import io.reactivex.Observable

/**
 * Created by felip on 09/03/2018.
 */
class RetrofitTwitterDataSource(
        private val twitterAuthService: TwitterAuthService,
        private val twitterService: TwitterService,
        private val applicationPreferences: ApplicationPreferences) : TwitterDataSource {

    override fun getUser(userName: String): Observable<User> {
        return ensureAuthenticationBeforeApiCall()
                .flatMap { twitterService.usersShow(userName) }
                .map { UserMapper.mapRetrofitToDomain(it) }
    }

    override fun getTweetsSinceTweet(user: User, tweet: Tweet): Observable<Tweet> {
        return ensureAuthenticationBeforeApiCall()
                .flatMap { twitterService.statusesUserTimeline(user.userName, tweet.id) }
                .flatMap { Observable.fromIterable(it) }
                .map { TweetMapper.mapTwitterApiToDomain(it) }
    }

    override fun getLatestTweetsFromUser(user: User): Observable<Tweet> {
        return ensureAuthenticationBeforeApiCall()
                .flatMap { twitterService.statusesUserTimeline(user.userName) }
                .flatMap { Observable.fromIterable(it) }
                .map { TweetMapper.mapTwitterApiToDomain(it) }
    }

    private fun ensureAuthenticationBeforeApiCall(): Observable<String> {
        return if (applicationPreferences.hasTwitterAccessToken())
            Observable.just(applicationPreferences.retrieveTwitterAccessToken())
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
                .doOnNext { applicationPreferences.storeTwitterAccessToken(it) }
                .onErrorResumeNext(Observable.error(TwitterError.AuthenticationError()))
    }
}