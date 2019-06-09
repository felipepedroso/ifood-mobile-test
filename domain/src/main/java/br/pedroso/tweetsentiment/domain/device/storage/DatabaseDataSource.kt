package br.pedroso.tweetsentiment.domain.device.storage

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import io.reactivex.Flowable
import io.reactivex.Maybe

interface DatabaseDataSource {
    fun getUser(username: String): Flowable<User>
    fun registerUser(user: User)
    fun registerTweet(user: User, tweet: Tweet)
    fun getTweetsFromUser(user: User): Flowable<List<Tweet>>
    fun getLatestTweetFromUser(user: User): Maybe<Tweet>
    fun updateTweetSentiment(tweet: Tweet, sentiment: Sentiment)
    fun getUserRecordOnDatabase(username: String): Maybe<User>
}