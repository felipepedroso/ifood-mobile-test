package br.pedroso.tweetsentiment.app.di.storage.database

import androidx.room.Room
import br.pedroso.tweetsentiment.device.storage.database.RoomDataSource
import br.pedroso.tweetsentiment.device.storage.database.room.database.TweetSentimentDatabase
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TweetSentimentDatabase::class.java,
            "tweet-sentiment-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        val tweetSentimentDatabase: TweetSentimentDatabase = get()
        tweetSentimentDatabase.dao()
    }

    single<DatabaseDataSource> {
        RoomDataSource(
            tweetSentimentDao = get()
        )
    }
}
