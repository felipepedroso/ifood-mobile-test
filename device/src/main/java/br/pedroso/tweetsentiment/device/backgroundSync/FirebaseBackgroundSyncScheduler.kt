package br.pedroso.tweetsentiment.device.backgroundSync

import br.pedroso.tweetsentiment.device.backgroundSync.firebase.BackgroundSyncJobService
import br.pedroso.tweetsentiment.domain.device.backgroundSync.BackgroundSyncScheduler
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.Lifetime
import com.firebase.jobdispatcher.Trigger
import timber.log.Timber

/**
 * Created by felip on 11/03/2018.
 */
class FirebaseBackgroundSyncScheduler(val firebaseJobDispatcher: FirebaseJobDispatcher) : BackgroundSyncScheduler {
    override fun executeSyncNow() {
        var job = firebaseJobDispatcher.newJobBuilder()
                .setService(BackgroundSyncJobService::class.java)
                .setTag(JOB_TAG_NOW)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.NOW)
                .setReplaceCurrent(true)
                .build()

        firebaseJobDispatcher.mustSchedule(job)
        Timber.d("Scheduled ")
    }

    override fun scheduleRecurringSync(timeInterval: Int) {
        val recurringJob = firebaseJobDispatcher.newJobBuilder()
                .setService(BackgroundSyncJobService::class.java)
                .setTag(JOB_TAG_RECURRING)
                .setRecurring(true)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(timeInterval - 5, timeInterval + 5))
                .setReplaceCurrent(true)
                .build()

        firebaseJobDispatcher.mustSchedule(recurringJob)
    }

    override fun stopAllJobs() {
        firebaseJobDispatcher.cancelAll()
    }

    companion object {
        private const val JOB_TAG_NOW = "TweetSentimentBackgroundSyncNow"
        private const val JOB_TAG_RECURRING = "TweetSentimentBackgroundSyncRecurring"
    }
}