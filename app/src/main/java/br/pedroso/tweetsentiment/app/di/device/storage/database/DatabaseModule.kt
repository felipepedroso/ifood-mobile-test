package br.pedroso.tweetsentiment.app.di.device.storage.database

import android.arch.persistence.room.Room
import android.content.Context
import br.pedroso.tweetsentiment.app.di.DependenciesTags
import br.pedroso.tweetsentiment.device.storage.database.RoomDataSource
import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.database.TweetSentimentDatabase
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import com.github.salomonbrys.kodein.*

/**
 * Created by felipe on 09/03/2018.
 */
class DatabaseModule(context: Context) {
    val graph = Kodein.Module {
        bind<TweetSentimentDatabase>(DependenciesTags.DATABASE) with singleton {
            Room.databaseBuilder(context, TweetSentimentDatabase::class.java, "tweet-sentiment-db")
                    .fallbackToDestructiveMigration()
                    .build()
        }

        bind<TweetSentimentDao>() with provider {
            val tweetSentimentDatabase: TweetSentimentDatabase = instance(DependenciesTags.DATABASE)
            tweetSentimentDatabase.dao()
        }

        bind<DatabaseDataSource>() with provider {
            RoomDataSource(
                    tweetSentimentDao = instance()
            )
        }
    }
}