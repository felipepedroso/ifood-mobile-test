package br.pedroso.tweetsentiment.app.di.network

import br.pedroso.tweetsentiment.app.di.DependenciesTags.NETWORK_CACHE_DIR
import br.pedroso.tweetsentiment.app.di.network.naturallanguageapi.naturalLanguageApiModule
import br.pedroso.tweetsentiment.app.di.network.twitter.twitterModule
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

private const val NETWORK_CACHE_SIZE = 10L * 1024 * 1024

private val commonNetwork = module {
    single {
        Cache(get(named(NETWORK_CACHE_DIR)), NETWORK_CACHE_SIZE)
    }

    factory {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }
}

val networkModule = listOf(
    commonNetwork,
    twitterModule,
    naturalLanguageApiModule
)
