package br.pedroso.tweetsentiment.device.storage.database.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomTweet
import br.pedroso.tweetsentiment.device.storage.database.room.entities.RoomUser

@Database(
        version = 1,
        entities = [RoomTweet::class, RoomUser::class],
        exportSchema = false)
abstract class TweetSentimentDatabase : RoomDatabase() {
    abstract fun dao(): TweetSentimentDao
}