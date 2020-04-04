package br.pedroso.tweetsentiment.app.di.storage.cache

import br.pedroso.tweetsentiment.app.di.DependenciesTags.NETWORK_CACHE_DIR
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val cacheModule = module {
    single(named(NETWORK_CACHE_DIR)) {
        androidContext().cacheDir
    }
}
