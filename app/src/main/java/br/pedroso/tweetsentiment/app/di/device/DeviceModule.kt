package br.pedroso.tweetsentiment.app.di.device

import android.content.Context
import br.pedroso.tweetsentiment.app.di.device.storage.StorageModule
import com.github.salomonbrys.kodein.Kodein

class DeviceModule(private val context: Context) {
    val graph = Kodein.Module {
        import(StorageModule(context).graph)
    }
}
