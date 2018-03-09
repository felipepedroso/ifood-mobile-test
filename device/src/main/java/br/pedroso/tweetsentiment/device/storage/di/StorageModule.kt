package br.pedroso.tweetsentiment.device.storage.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felip on 08/03/2018.
 */
class StorageModule(private val context: Context) {
    val graph = Kodein.Module {
    }
}