package br.pedroso.tweetsentiment.device.storage.di

import android.content.Context
import br.pedroso.tweetsentiment.device.storage.database.di.DatabaseModule
import br.pedroso.tweetsentiment.device.storage.database.room.di.RoomModule
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felip on 08/03/2018.
 */
class StorageModule(private val context: Context) {
    val graph = Kodein.Module {
        import(DatabaseModule(context).graph)
    }
}