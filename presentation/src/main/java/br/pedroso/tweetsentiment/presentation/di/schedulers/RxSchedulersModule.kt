package br.pedroso.tweetsentiment.presentation.di.schedulers

import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.UI_SCHEDULER
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.WORKER_SCHEDULER
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by felip on 08/03/2018.
 */
class RxSchedulersModule {
    val graph = Kodein.Module {
        bind<Scheduler>(WORKER_SCHEDULER) with singleton { Schedulers.io() }
        bind<Scheduler>(UI_SCHEDULER) with singleton { AndroidSchedulers.mainThread() }
    }
}