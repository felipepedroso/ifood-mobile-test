package br.pedroso.tweetsentiment.app.di.network.naturallanguageapi

import br.pedroso.tweetsentiment.BuildConfig
import br.pedroso.tweetsentiment.app.di.DependenciesTags.NATURAL_LANGUAGE_OKHTTP_CLIENT
import br.pedroso.tweetsentiment.app.di.DependenciesTags.NATURAL_LANGUAGE_RETROFIT
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import br.pedroso.tweetsentiment.network.naturalLanguageApi.NaturalLanguageApiDataSource
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.NaturalLanguageApiService
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.constants.NATURAL_LANGUAGE_API_BASE_URL
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.interceptors.NaturalLanguageApiAddKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val naturalLanguageApiModule = module {
    single {
        NaturalLanguageApiAddKeyInterceptor(BuildConfig.NATURAL_LANGUAGE_API_KEY)
    }

    single(named(NATURAL_LANGUAGE_OKHTTP_CLIENT)) {
        OkHttpClient.Builder().apply {
            addInterceptor(get<NaturalLanguageApiAddKeyInterceptor>())

            if (BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>())
            }

            cache(get())
        }.build()
    }

    single(named(NATURAL_LANGUAGE_RETROFIT)) {
        Retrofit.Builder().apply {
            addConverterFactory(get())
            addCallAdapterFactory(get())
            baseUrl(NATURAL_LANGUAGE_API_BASE_URL)
            client(get(named(NATURAL_LANGUAGE_OKHTTP_CLIENT)))
        }.build()
    }

    single {
        val retrofit: Retrofit = get(named(NATURAL_LANGUAGE_RETROFIT))
        retrofit.create(NaturalLanguageApiService::class.java)
    }

    single<SentimentAnalysisDataSource> {
        NaturalLanguageApiDataSource(
            naturalLanguageApiService = get()
        )
    }
}
