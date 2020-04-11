package br.pedroso.tweetsentiment.device.storage.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomTweet
import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomUser
import kotlinx.coroutines.flow.Flow

@Dao
interface TweetSentimentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerTweet(roomTweet: RoomTweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUsers(vararg roomUser: RoomUser)

    @Query("SELECT * FROM tweets WHERE user_id = :userId ORDER BY created_at DESC")
    fun getAllTweetsFromUser(userId: Long): Flow<List<RoomTweet>>

    @Query("SELECT * FROM users WHERE userName = :userName")
    fun getUser(userName: String): Flow<RoomUser>

    @Query("SELECT * FROM users WHERE userName = :userName")
    suspend fun getUserRecord(userName: String): RoomUser?

    @Query("SELECT * FROM tweets WHERE user_id = :userId ORDER BY created_at DESC LIMIT 1")
    suspend fun getLatestTweetFromUser(userId: Long): RoomTweet?

    @Query("SELECT * FROM tweets WHERE id = :id LIMIT 1")
    suspend fun getTweetById(id: Long): RoomTweet?

    @Update
    suspend fun updateTweet(roomTweet: RoomTweet)
}
