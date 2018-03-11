package br.pedroso.tweetsentiment.presentation.features.bootSyncScheduler.di

import br.pedroso.tweetsentiment.presentation.features.bootSyncScheduler.BootSyncScheduler
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidBroadcastReceiverScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.scopedSingleton

/**
 * Created by felip on 11/03/2018.
 */
class BootSyncSchedulerModule {
    val graph = Kodein.Module {
        bind<BootSyncScheduler>() with scopedSingleton(androidBroadcastReceiverScope) {
            BootSyncScheduler(
                    backgroundSyncScheduler = instance(),
                    applicationPreferences = instance()
            )
        }
    }
}