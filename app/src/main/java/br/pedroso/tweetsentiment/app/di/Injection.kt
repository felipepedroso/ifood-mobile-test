package br.pedroso.tweetsentiment.app.di

import android.app.Application
import br.pedroso.tweetsentiment.app.di.device.DeviceModule
import br.pedroso.tweetsentiment.app.di.network.NetworkModule
import br.pedroso.tweetsentiment.app.di.presentation.PresentationModule
import br.pedroso.tweetsentiment.app.di.schedulers.SchedulersModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.lazy

class Injection(private val application: Application) {
    init {
        application.registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }

    val graph = Kodein.lazy {
        import(SchedulersModule().graph)
        import(DeviceModule(application).graph)
        import(NetworkModule().graph)
        import(PresentationModule().graph)
    }
}
