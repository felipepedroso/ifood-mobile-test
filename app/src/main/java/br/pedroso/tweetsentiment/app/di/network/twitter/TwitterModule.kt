package br.pedroso.tweetsentiment.app.di.network.twitter

import br.pedroso.tweetsentiment.BuildConfig
import br.pedroso.tweetsentiment.app.di.DependenciesTags
import br.pedroso.tweetsentiment.app.di.DependenciesTags.TWITTER_AUTH_OKHTTP_CLIENT
import br.pedroso.tweetsentiment.app.di.DependenciesTags.TWITTER_AUTH_RETROFIT
import br.pedroso.tweetsentiment.app.di.DependenciesTags.TWITTER_OKHTTP_CLIENT
import br.pedroso.tweetsentiment.app.di.DependenciesTags.TWITTER_RETROFIT
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.network.twitter.RetrofitTwitterDataSource
import br.pedroso.tweetsentiment.network.twitter.retrofit.constants.TWITTER_API_BASE_URL
import br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors.TwitterCredentialsAuthInterceptor
import br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors.TwitterRequestBearerInterceptor
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterAuthService
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val twitterModule = module {
    single<TwitterDataSource> {
        RetrofitTwitterDataSource(
            workerScheduler = get(named(DependenciesTags.WORKER_SCHEDULER)),
            twitterAuthService = get(),
            twitterService = get(),
            applicationSettings = get()
        )
    }

    single {
        TwitterRequestBearerInterceptor(
            applicationSettings = get()
        )
    }

    single(named(TWITTER_OKHTTP_CLIENT)) {
        OkHttpClient.Builder().apply {
            addInterceptor(get<TwitterRequestBearerInterceptor>())

            if (BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>())
            }

            cache(get())
        }.build()
    }

    single(named(TWITTER_RETROFIT)) {
        Retrofit.Builder().apply {
            baseUrl(TWITTER_API_BASE_URL)
            addConverterFactory(get())
            client(get(named(TWITTER_OKHTTP_CLIENT)))
        }.build()
    }

    single {
        val retrofit: Retrofit = get(named(TWITTER_RETROFIT))
        retrofit.create(TwitterService::class.java)
    }

    single {
        TwitterCredentialsAuthInterceptor(
            twitterConsumerKey = BuildConfig.TWITTER_CONSUMER_KEY,
            twitterConsumerSecret = BuildConfig.TWITTER_CONSUMER_SECRET
        )
    }

    single(named(TWITTER_AUTH_OKHTTP_CLIENT)) {
        OkHttpClient.Builder().apply {
            addInterceptor(get<TwitterCredentialsAuthInterceptor>())

            if (BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>())
            }

            cache(get())
        }.build()
    }

    single(named(TWITTER_AUTH_RETROFIT)) {
        Retrofit.Builder().apply {
            baseUrl(TWITTER_API_BASE_URL)
            addConverterFactory(get())
            client(get(named(TWITTER_AUTH_OKHTTP_CLIENT)))
        }.build()
    }

    single {
        val retrofit: Retrofit = get(named(TWITTER_AUTH_RETROFIT))
        retrofit.create(TwitterAuthService::class.java)
    }
}
