package br.pedroso.tweetsentiment.app.di.device.storage.settings

import android.content.Context
import br.pedroso.tweetsentiment.device.storage.applicationSettings.HawkApplicationSettings
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton

class SettingsModule(private val context: Context) {
    val graph = Kodein.Module {
        bind<ApplicationSettings>() with singleton {
            HawkApplicationSettings(
                    context = context
            )
        }
    }
}