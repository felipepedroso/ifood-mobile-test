package br.pedroso.tweetsentiment.presentation.features.bootSyncScheduler

import br.pedroso.tweetsentiment.domain.device.backgroundSync.BackgroundSyncScheduler
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences

/**
 * Created by felip on 11/03/2018.
 */
class BootSyncScheduler(
        private val applicationPreferences: ApplicationPreferences,
        private val backgroundSyncScheduler: BackgroundSyncScheduler) {

    fun bootHasCompleted() {
        val timeInterval = applicationPreferences.retrieveRecurrentSyncTimeInterval()

        backgroundSyncScheduler.stopAllJobs()
        backgroundSyncScheduler.scheduleRecurringSync(timeInterval)
    }
}