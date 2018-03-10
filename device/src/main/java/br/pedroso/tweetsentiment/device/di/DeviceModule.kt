package br.pedroso.tweetsentiment.device.di

import android.content.Context
import br.pedroso.tweetsentiment.device.storage.di.StorageModule
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felipe on 09/03/2018.
 */
class DeviceModule(private val context: Context) {
    val graph = Kodein.Module {
        import(StorageModule(context).graph)
    }
}