package br.pedroso.tweetsentiment.app.di.network.twitter

import br.pedroso.tweetsentiment.BuildConfig
import br.pedroso.tweetsentiment.app.di.DependenciesTags
import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.network.twitter.RetrofitTwitterDataSource
import br.pedroso.tweetsentiment.network.twitter.retrofit.constants.TWITTER_API_BASE_URL
import br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors.TwitterCredentialsAuthInterceptor
import br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors.TwitterRequestBearerInterceptor
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterAuthService
import br.pedroso.tweetsentiment.network.twitter.retrofit.services.TwitterService
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

/**
 * Created by felip on 09/03/2018.
 */
class TwitterModule {
    val graph = Kodein.Module {
        bind<TwitterDataSource>() with singleton {
            RetrofitTwitterDataSource(
                    twitterService = instance(),
                    twitterAuthService = instance(),
                    applicationSettings = instance()
            )
        }

        bind<TwitterRequestBearerInterceptor>() with singleton {
            TwitterRequestBearerInterceptor(
                    applicationSettings = instance()
            )
        }

        bind<OkHttpClient>(DependenciesTags.TWITTER_OKHTTP_CLIENT) with singleton {
            val builder = OkHttpClient.Builder()

            val twitterRequestBearerInterceptor: TwitterRequestBearerInterceptor = instance()
            builder.addInterceptor(twitterRequestBearerInterceptor)

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

        bind<Retrofit>(DependenciesTags.TWITTER_RETROFIT) with singleton {
            Retrofit.Builder()
                    .baseUrl(TWITTER_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(instance(DependenciesTags.TWITTER_OKHTTP_CLIENT))
                    .build()
        }

        bind<TwitterService>() with singleton {
            val retrofit: Retrofit = instance(DependenciesTags.TWITTER_RETROFIT)
            retrofit.create(TwitterService::class.java)
        }

        bind<TwitterCredentialsAuthInterceptor>() with singleton {
            TwitterCredentialsAuthInterceptor(
                    twitterConsumerKey = BuildConfig.TWITTER_CONSUMER_KEY,
                    twitterConsumerSecret = BuildConfig.TWITTER_CONSUMER_SECRET
            )
        }

        bind<OkHttpClient>(DependenciesTags.TWITTER_AUTH_OKHTTP_CLIENT) with singleton {
            val builder = OkHttpClient.Builder()

            val interceptor: TwitterCredentialsAuthInterceptor = instance()
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

        bind<Retrofit>(DependenciesTags.TWITTER_AUTH_RETROFIT) with singleton {
            Retrofit.Builder()
                    .baseUrl(TWITTER_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(instance(DependenciesTags.TWITTER_AUTH_OKHTTP_CLIENT))
                    .build()

        }

        bind<TwitterAuthService>() with singleton {
            val retrofit: Retrofit = instance(DependenciesTags.TWITTER_AUTH_RETROFIT)
            retrofit.create(TwitterAuthService::class.java)
        }
    }
}