package br.pedroso.tweetsentiment.app.di.device.storage.database

import android.content.Context
import androidx.room.Room
import br.pedroso.tweetsentiment.app.di.DependenciesTags
import br.pedroso.tweetsentiment.device.storage.database.RoomDataSource
import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.database.TweetSentimentDatabase
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton

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
