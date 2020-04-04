package br.pedroso.tweetsentiment.app.di.storage.settings

import br.pedroso.tweetsentiment.device.storage.applicationSettings.HawkApplicationSettings
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val settingsModule = module {
    single<ApplicationSettings> {
        HawkApplicationSettings(
            context = androidContext()
        )
    }
}
