package br.pedroso.tweetsentiment.app.di.device

import android.content.Context
import br.pedroso.tweetsentiment.app.di.device.backgroundSync.BackgroundSyncModule
import br.pedroso.tweetsentiment.app.di.device.storage.StorageModule
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felipe on 09/03/2018.
 */
class DeviceModule(private val context: Context) {
    val graph = Kodein.Module {
        import(StorageModule(context).graph)
        import(BackgroundSyncModule(context).graph)
    }
}