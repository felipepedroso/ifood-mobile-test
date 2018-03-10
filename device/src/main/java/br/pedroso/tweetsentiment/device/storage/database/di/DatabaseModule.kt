package br.pedroso.tweetsentiment.device.storage.database.di

import android.content.Context
import br.pedroso.tweetsentiment.device.storage.database.room.RoomDataSource
import br.pedroso.tweetsentiment.device.storage.database.room.di.RoomModule
import br.pedroso.tweetsentiment.domain.device.storage.DatabaseDataSource
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider

/**
 * Created by felipe on 09/03/2018.
 */
class DatabaseModule(context: Context) {
    val graph = Kodein.Module {
        import(RoomModule(context).graph)

        bind<DatabaseDataSource>() with provider {
            RoomDataSource(
                    tweetSentimentDao = instance()
            )
        }
    }
}