package br.pedroso.tweetsentiment.app.di.dispatchers

import br.pedroso.tweetsentiment.app.di.DependenciesTags.WORKER_DISPATCHER
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
    single(named(WORKER_DISPATCHER)) {
        Dispatchers.IO
    }
}
