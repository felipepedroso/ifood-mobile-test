package br.pedroso.tweetsentiment.app

import android.app.Application
import br.pedroso.tweetsentiment.app.di.Injection
import com.github.salomonbrys.kodein.KodeinAware
import timber.log.Timber

class TweetSentimentApplication : Application(), KodeinAware {
    override val kodein by Injection(this).graph

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }
}