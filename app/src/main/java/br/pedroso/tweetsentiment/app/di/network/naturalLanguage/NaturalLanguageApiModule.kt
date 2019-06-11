package br.pedroso.tweetsentiment.app.di.network.naturalLanguage

import br.pedroso.tweetsentiment.BuildConfig
import br.pedroso.tweetsentiment.app.di.DependenciesTags.NATURAL_LANGUAGE_OKHTTP_CLIENT
import br.pedroso.tweetsentiment.app.di.DependenciesTags.NATURAL_LANGUAGE_RETROFIT
import br.pedroso.tweetsentiment.domain.network.dataSources.SentimentAnalysisDataSource
import br.pedroso.tweetsentiment.network.naturalLanguageApi.NaturalLanguageApiDataSource
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.NaturalLanguageApiService
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.constants.NATURAL_LANGUAGE_API_BASE_URL
import br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.interceptors.NaturalLanguageApiAddKeyInterceptor
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NaturalLanguageApiModule {
    val graph = Kodein.Module {

        bind<NaturalLanguageApiAddKeyInterceptor>() with singleton {
            NaturalLanguageApiAddKeyInterceptor(BuildConfig.NATURAL_LANGUAGE_API_KEY)
        }

        bind<OkHttpClient>(NATURAL_LANGUAGE_OKHTTP_CLIENT) with singleton {
            val builder = OkHttpClient.Builder()

            val interceptor: NaturalLanguageApiAddKeyInterceptor = instance()
            builder.addInterceptor(interceptor)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
                builder.addInterceptor(loggingInterceptor)
            }

            val cache: Cache = instance()
            builder.cache(cache)

            builder.build()
        }

        bind<Retrofit>(NATURAL_LANGUAGE_RETROFIT) with singleton {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NATURAL_LANGUAGE_API_BASE_URL)
                .client(instance(NATURAL_LANGUAGE_OKHTTP_CLIENT))
                .build()
        }

        bind<NaturalLanguageApiService>() with singleton {
            val retrofit: Retrofit = instance(NATURAL_LANGUAGE_RETROFIT)

            retrofit.create(NaturalLanguageApiService::class.java)
        }

        bind<SentimentAnalysisDataSource>() with singleton {
            NaturalLanguageApiDataSource(
                naturalLanguageApiService = instance()
            )
        }
    }
}
