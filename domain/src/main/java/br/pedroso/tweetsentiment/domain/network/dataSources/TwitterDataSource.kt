package br.pedroso.tweetsentiment.domain.network.dataSources

import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User

interface TwitterDataSource {
    suspend fun getUser(userName: String): User
    suspend fun getTweetsSinceTweet(user: User, tweet: Tweet): List<Tweet>
    suspend fun getLatestTweetsFromUser(user: User): List<Tweet>
}
