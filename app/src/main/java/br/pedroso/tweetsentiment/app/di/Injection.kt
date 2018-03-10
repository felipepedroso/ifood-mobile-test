package br.pedroso.tweetsentiment.app.di

import android.app.Application
import br.pedroso.tweetsentiment.app.di.cacheDir.CacheDirModule
import br.pedroso.tweetsentiment.network.di.NetworkModule
import br.pedroso.tweetsentiment.device.di.DeviceModule
import br.pedroso.tweetsentiment.presentation.di.PresentationModule
import br.pedroso.tweetsentiment.app.di.schedulers.RxSchedulersModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.lazy

/**
 * Created by felip on 08/03/2018.
 */
class Injection(private val application: Application) {
    init {
        application.registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }

    val graph = Kodein.lazy {
        import(RxSchedulersModule().graph)
        import(CacheDirModule(application).graph)
        import(DeviceModule(application).graph)
        import(NetworkModule().graph)
        import(PresentationModule().graph)
    }
}