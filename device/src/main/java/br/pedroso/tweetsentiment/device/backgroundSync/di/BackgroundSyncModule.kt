package br.pedroso.tweetsentiment.device.backgroundSync.di

import android.content.Context
import br.pedroso.tweetsentiment.device.backgroundSync.firebase.FirebaseBackgroundSyncScheduler
import br.pedroso.tweetsentiment.device.backgroundSync.firebase.di.FirebaseJobDispatcherModule
import br.pedroso.tweetsentiment.domain.device.backgroundSync.BackgroundSyncScheduler
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by felip on 11/03/2018.
 */
class BackgroundSyncModule(private val context: Context) {
    val graph = Kodein.Module {
        import(FirebaseJobDispatcherModule(context).graph)

        bind<BackgroundSyncScheduler>() with singleton {
            FirebaseBackgroundSyncScheduler(
                    firebaseJobDispatcher = instance()
            )
        }
    }
}