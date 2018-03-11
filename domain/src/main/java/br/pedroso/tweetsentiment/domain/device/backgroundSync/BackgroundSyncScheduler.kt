package br.pedroso.tweetsentiment.domain.device.backgroundSync

/**
 * Created by felip on 11/03/2018.
 */
interface BackgroundSyncScheduler {
    fun executeSyncNow()
    fun scheduleRecurringSync(timeInterval: Int)
    fun stopAllJobs()
}