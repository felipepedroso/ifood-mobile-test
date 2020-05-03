package br.pedroso.tweetsentiment.app

import android.app.Application
import br.pedroso.tweetsentiment.BuildConfig
import br.pedroso.tweetsentiment.app.di.dispatchers.dispatchersModule
import br.pedroso.tweetsentiment.app.di.features.featuresModules
import br.pedroso.tweetsentiment.app.di.network.networkModule
import br.pedroso.tweetsentiment.app.di.storage.storageModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TweetSentimentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@TweetSentimentApplication)
            modules(storageModules)
            modules(networkModule)
            modules(featuresModules)
            modules(dispatchersModule)
        }
    }
}
