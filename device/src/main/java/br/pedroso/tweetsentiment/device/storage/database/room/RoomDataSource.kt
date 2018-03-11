package br.pedroso.tweetsentiment.device.storage.database.room

import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomTweetMapper
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomUserMapper
import br.pedroso.tweetsentiment.domain.Sentiment
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by felipe on 09/03/2018.
 */
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

    override fun getTweetsFromUser(user: User): Flowable<Tweet> {
        return tweetSentimentDao.getAllTweetsFromUser(user.id)
                .flatMap { Flowable.fromIterable(it) }
                .map { RoomTweetMapper.mapRoomToDomain(it) }
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