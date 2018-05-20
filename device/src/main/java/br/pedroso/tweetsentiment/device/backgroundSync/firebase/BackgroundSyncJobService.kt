package br.pedroso.tweetsentiment.device.backgroundSync.firebase

import br.pedroso.tweetsentiment.presentation.features.backgroundSync.BackgroundSyncCoordinator
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with

/**
 * Created by felip on 11/03/2018.
 */
class BackgroundSyncJobService : JobService() {
    private val kodein by lazy { LazyKodein(appKodein) }
    val coordinator by kodein.with(this).instance<BackgroundSyncCoordinator>()

    override fun onStopJob(job: JobParameters?): Boolean {
        return coordinator.stopSyncing()
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        return coordinator.startSyncing()
    }
}