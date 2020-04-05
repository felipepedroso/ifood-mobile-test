package br.pedroso.tweetsentiment.device.storage.database

import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomTweetMapper
import br.pedroso.tweetsentiment.device.storage.database.room.mappers.RoomUserMapper
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Scheduler

class RoomDataSource(
    private val workerScheduler: Scheduler,
    private val tweetSentimentDao: TweetSentimentDao
) : DatabaseDataSource {
    override fun getUserRecordOnDatabase(username: String): Maybe<User> {
        return tweetSentimentDao.getUserRecord(username)
            .map { RoomUserMapper.mapRoomToDomain(it) }
            .subscribeOn(workerScheduler)
    }

    override fun updateTweetSentiment(tweet: Tweet, sentiment: Sentiment): Completable {
        return Completable.defer {
            Completable.fromAction {
                val updatedRoomTweet =
                    tweetSentimentDao.getTweetById(tweet.id).copy(sentiment = sentiment.name)
                tweetSentimentDao.updateTweet(updatedRoomTweet)
            }
        }.subscribeOn(workerScheduler)
    }

    override fun getLatestTweetFromUser(user: User): Maybe<Tweet> {
        return tweetSentimentDao.getLatestTweetFromUser(user.id)
            .map { RoomTweetMapper.mapRoomToDomain(it) }
            .subscribeOn(workerScheduler)
    }

    override fun getTweetsFromUser(user: User): Flowable<List<Tweet>> {
        return tweetSentimentDao.getAllTweetsFromUser(user.id)
            .map { it -> it.map { RoomTweetMapper.mapRoomToDomain(it) } }
            .subscribeOn(workerScheduler)
    }

    override fun registerTweet(user: User, tweet: Tweet): Completable {
        return Completable.defer {
            Completable.fromAction {
                val roomTweet = RoomTweetMapper.mapDomainToRoom(user, tweet)
                tweetSentimentDao.registerTweet(roomTweet)
            }
        }.subscribeOn(workerScheduler)
    }

    override fun getUser(username: String): Flowable<User> {
        return tweetSentimentDao.getUser(username)
            .map { RoomUserMapper.mapRoomToDomain(it) }
            .subscribeOn(workerScheduler)
    }

    override fun registerUser(user: User): Completable {
        return Completable.defer {
            Completable.fromAction {
                val roomUser = RoomUserMapper.mapDomainToRoom(user)
                tweetSentimentDao.registerUsers(roomUser)
            }
        }.subscribeOn(workerScheduler)
    }
}
