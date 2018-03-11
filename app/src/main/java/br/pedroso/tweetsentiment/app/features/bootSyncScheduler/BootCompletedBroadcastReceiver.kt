package br.pedroso.tweetsentiment.app.features.bootSyncScheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.pedroso.tweetsentiment.presentation.features.bootSyncScheduler.BootSyncScheduler
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.BroadcastReceiverInjector
import com.github.salomonbrys.kodein.instance

/**
 * Created by felip on 11/03/2018.
 */
class BootCompletedBroadcastReceiver : BroadcastReceiver(), BroadcastReceiverInjector {
    override val injector = KodeinInjector()

    override var context: Context? = null

    private val bootCompletedSyncScheduler: BootSyncScheduler by injector.instance()

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context

        initializeInjector()

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            bootCompletedSyncScheduler.bootHasCompleted()
        }

        destroyInjector()
    }
}