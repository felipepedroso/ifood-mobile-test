package br.pedroso.tweetsentiment.app.di.device.storage

import android.content.Context
import br.pedroso.tweetsentiment.app.di.device.storage.database.DatabaseModule
import br.pedroso.tweetsentiment.app.di.device.storage.filesystem.CacheDirModule
import br.pedroso.tweetsentiment.app.di.device.storage.settings.SettingsModule
import com.github.salomonbrys.kodein.Kodein

class StorageModule(private val context: Context) {
    val graph = Kodein.Module {
        import(CacheDirModule(context).graph)
        import(DatabaseModule(context).graph)
        import(SettingsModule(context).graph)
    }
}
