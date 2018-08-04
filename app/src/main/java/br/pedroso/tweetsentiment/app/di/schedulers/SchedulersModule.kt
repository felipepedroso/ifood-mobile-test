package br.pedroso.tweetsentiment.app.di.schedulers

import br.pedroso.tweetsentiment.app.di.DependenciesTags.UI_SCHEDULER
import br.pedroso.tweetsentiment.app.di.DependenciesTags.WORKER_SCHEDULER
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersModule {
    val graph = Kodein.Module {
        bind<Scheduler>(WORKER_SCHEDULER) with singleton { Schedulers.io() }
        bind<Scheduler>(UI_SCHEDULER) with singleton { AndroidSchedulers.mainThread() }
    }
}