package br.pedroso.tweetsentiment.device.storage.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomTweet
import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomUser
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface TweetSentimentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerTweet(roomTweet: RoomTweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerUsers(vararg roomUser: RoomUser)

    @Query("SELECT * FROM tweets WHERE userId = :userId")
    fun getAllTweetsFromUser(userId: Long): Flowable<List<RoomTweet>>

    @Query("SELECT * FROM users WHERE userName = :userName")
    fun getUser(userName: String): Flowable<RoomUser>

    @Query("SELECT * FROM users WHERE userName = :userName")
    fun getUserRecord(userName: String): Maybe<RoomUser>

    @Query("SELECT * FROM tweets WHERE userId = :userId ORDER BY createdAtTimestamp DESC LIMIT 1")
    fun getLatestTweetFromUser(userId: Long): Maybe<RoomTweet>

    @Query("SELECT * FROM tweets WHERE id = :id LIMIT 1")
    fun getTweetById(id: Long): RoomTweet

    @Update
    fun updateTweet(roomTweet: RoomTweet)
}
