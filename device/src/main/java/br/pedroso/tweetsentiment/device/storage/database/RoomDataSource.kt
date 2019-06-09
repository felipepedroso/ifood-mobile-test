package br.pedroso.tweetsentiment.device.storage.database

import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomTweetMapper
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomUserMapper
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe

class RoomDataSource(val tweetSentimentDao: TweetSentimentDao) : DatabaseDataSource {
    override fun getUserRecordOnDatabase(username: String): Maybe<User> {
        return tweetSentimentDao.getUserRecord(username)
                .map { RoomUserMapper.mapRoomToDomain(it) }
    }

    override fun updateTweetSentiment(tweet: Tweet, sentiment: Sentiment) {
        val updatedRoomTweet = tweetSentimentDao.getTweetById(tweet.id).copy(sentiment = sentiment.name)
        tweetSentimentDao.updateTweet(updatedRoomTweet)
    }

    override fun getLatestTweetFromUser(user: User): Maybe<Tweet> {
        return tweetSentimentDao.getLatestTweetFromUser(user.id)
                .map { RoomTweetMapper.mapRoomToDomain(it) }
    }

    override fun getTweetsFromUser(user: User): Flowable<List<Tweet>> {
        return tweetSentimentDao.getAllTweetsFromUser(user.id)
                .map { it -> it.map { RoomTweetMapper.mapRoomToDomain(it) } }
    }

    override fun registerTweet(user: User, tweet: Tweet) {
        val roomTweet = RoomTweetMapper.mapDomainToRoom(user, tweet)
        tweetSentimentDao.registerTweet(roomTweet)
    }

    override fun getUser(username: String): Flowable<User> {
        return tweetSentimentDao.getUser(username)
                .map { RoomUserMapper.mapRoomToDomain(it) }
    }

    override fun registerUser(user: User) {
        val roomUser = RoomUserMapper.mapDomainToRoom(user)

        tweetSentimentDao.registerUsers(roomUser)
    }
}