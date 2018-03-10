package br.pedroso.tweetsentiment.device.storage.preferences.di

import android.content.Context
import br.pedroso.tweetsentiment.device.storage.preferences.hawk.HawkApplicationPreferences
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton

/**
 * Created by felip on 09/03/2018.
 */
class PreferencesModule(private val context: Context) {
    val graph = Kodein.Module {
        bind<ApplicationPreferences>() with singleton {
            HawkApplicationPreferences(
                    context = context
            )
        }
    }
}