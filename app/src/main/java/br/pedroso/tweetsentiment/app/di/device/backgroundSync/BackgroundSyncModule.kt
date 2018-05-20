package br.pedroso.tweetsentiment.app.di.device.backgroundSync

import android.content.Context
import br.pedroso.tweetsentiment.device.backgroundSync.FirebaseBackgroundSyncScheduler
import br.pedroso.tweetsentiment.domain.device.backgroundSync.BackgroundSyncScheduler
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by felip on 11/03/2018.
 */
class BackgroundSyncModule(private val context: Context) {
    val graph = Kodein.Module {
        bind<FirebaseJobDispatcher>() with singleton {
            FirebaseJobDispatcher(GooglePlayDriver(context))
        }

        bind<BackgroundSyncScheduler>() with singleton {
            FirebaseBackgroundSyncScheduler(
                    firebaseJobDispatcher = instance()
            )
        }
    }
}