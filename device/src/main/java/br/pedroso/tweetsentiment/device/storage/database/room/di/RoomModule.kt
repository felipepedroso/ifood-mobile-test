package br.pedroso.tweetsentiment.device.storage.database.room.di

import android.arch.persistence.room.Room
import android.content.Context
import br.pedroso.tweetsentiment.device.storage.database.room.dao.TweetSentimentDao
import br.pedroso.tweetsentiment.device.storage.database.room.database.TweetSentimentDatabase
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.DATABASE
import com.github.salomonbrys.kodein.*

/**
 * Created by felipe on 09/03/2018.
 */
class RoomModule(context: Context) {
    val graph = Kodein.Module {
        bind<TweetSentimentDatabase>(DATABASE) with singleton {
            Room.databaseBuilder(context, TweetSentimentDatabase::class.java, "tweet-sentiment-db")
                    .fallbackToDestructiveMigration()
                    .build()
        }

        bind<TweetSentimentDao>() with provider {
            val tweetSentimentDatabase: TweetSentimentDatabase = instance(DATABASE)
            tweetSentimentDatabase.dao()
        }
    }
}