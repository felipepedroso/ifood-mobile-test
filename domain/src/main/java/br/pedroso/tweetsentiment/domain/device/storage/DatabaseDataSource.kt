package br.pedroso.tweetsentiment.domain.device.storage

import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface DatabaseDataSource {
    fun getUser(username: String): Flow<User>
    suspend fun registerUser(user: User)
    suspend fun registerTweet(user: User, tweet: Tweet)
    fun getTweetsFromUser(user: User): Flow<List<Tweet>>
    suspend fun getLatestTweetFromUser(user: User): Tweet?
    suspend fun updateTweetSentiment(tweet: Tweet, sentiment: Sentiment)
    suspend fun getUserRecordOnDatabase(username: String): User?
}
