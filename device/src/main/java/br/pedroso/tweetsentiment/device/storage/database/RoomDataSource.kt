package br.pedroso.tweetsentiment.device.storage.database

import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomTweetMapper
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomUserMapper
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomDataSource(
    private val dispatcher: CoroutineDispatcher,
    private val tweetSentimentDao: TweetSentimentDao
) : DatabaseDataSource {

    override suspend fun getUserRecordOnDatabase(username: String): User? {
        return withContext(dispatcher) {
            val roomUser = tweetSentimentDao.getUserRecord(username)

            if (roomUser != null) {
                RoomUserMapper.mapRoomToDomain(roomUser)
            } else {
                null
            }
        }
    }

    override suspend fun updateTweetSentiment(tweet: Tweet, sentiment: Sentiment) {
        withContext(dispatcher) {
            val tweetToUpdate =
                tweetSentimentDao.getTweetById(tweet.id)

            if (tweetToUpdate != null) {
                tweetSentimentDao.updateTweet(tweetToUpdate.copy(sentiment = sentiment.name))
            }
        }
    }

    override suspend fun getLatestTweetFromUser(user: User): Tweet? {
        return withContext(dispatcher) {
            val roomTweet = tweetSentimentDao.getLatestTweetFromUser(user.id)

            if (roomTweet != null) {
                RoomTweetMapper.mapRoomToDomain(roomTweet)
            } else {
                null
            }
        }
    }

    override fun getTweetsFromUser(user: User): Flow<List<Tweet>> {
        return tweetSentimentDao.getAllTweetsFromUser(user.id)
            .map { it -> it.map { RoomTweetMapper.mapRoomToDomain(it) } }
            .flowOn(dispatcher)
    }

    override suspend fun registerTweet(user: User, tweet: Tweet) {
        withContext(dispatcher) {
            val roomTweet = RoomTweetMapper.mapDomainToRoom(user, tweet)
            tweetSentimentDao.registerTweet(roomTweet)
        }
    }

    override fun getUser(username: String): Flow<User> {
        return tweetSentimentDao.getUser(username)
            .map { RoomUserMapper.mapRoomToDomain(it) }
            .flowOn(dispatcher)
    }

    override suspend fun registerUser(user: User) {
        withContext(dispatcher) {
            val roomUser = RoomUserMapper.mapDomainToRoom(user)
            tweetSentimentDao.registerUsers(roomUser)
        }
    }
}
