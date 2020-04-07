package br.pedroso.tweetsentiment.app.di.schedulers

import br.pedroso.tweetsentiment.app.di.DependenciesTags.UI_SCHEDULER
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val schedulersModule = module {
    single(named(UI_SCHEDULER)) {
        AndroidSchedulers.mainThread()
    }
}
