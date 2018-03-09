package br.pedroso.tweetsentiment.domain.device.storage

import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by felip on 08/03/2018.
 */
interface DatabaseDataSource {
    fun getUser(username: String): Maybe<User>
    fun getFlowableUser(username: String): Flowable<User>
    fun registerUser(user: User)
    fun registerTweet(user: User, tweet: Tweet)
    fun getTweetsFromUser(user: User): Flowable<Tweet>
    fun getLatestTweetFromUser(user: User): Maybe<Tweet>
    fun updateTweetSentiment(tweet: Tweet, sentiment: Sentiment)
}