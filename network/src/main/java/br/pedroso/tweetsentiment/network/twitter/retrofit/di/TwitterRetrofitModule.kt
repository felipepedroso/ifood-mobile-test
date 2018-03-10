package br.pedroso.tweetsentiment.network.twitter.retrofit.di

import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.TWITTER_AUTH_OKHTTP_CLIENT
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.TWITTER_AUTH_RETROFIT
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.TWITTER_OKHTTP_CLIENT
import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.TWITTER_RETROFIT
import br.pedroso.tweetsentiment.network.BuildConfig
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
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by felip on 09/03/2018.
 */
class TwitterRetrofitModule {
    val graph = Kodein.Module {

        bind<TwitterRequestBearerInterceptor>() with singleton {
            TwitterRequestBearerInterceptor(
                    applicationPreferences = instance()
            )
        }

        bind<OkHttpClient>(TWITTER_OKHTTP_CLIENT) with singleton {
            val builder = OkHttpClient.Builder()

            val twitterRequestBearerInterceptor: TwitterRequestBearerInterceptor = instance()
            builder.addInterceptor(twitterRequestBearerInterceptor)

            val cache: Cache = instance()
            builder.cache(cache)

            builder.build()
        }

        bind<Retrofit>(TWITTER_RETROFIT) with singleton {
            Retrofit.Builder()
                    .baseUrl(TWITTER_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(instance(TWITTER_OKHTTP_CLIENT))
                    .build()
        }

        bind<TwitterService>() with singleton {
            val retrofit: Retrofit = instance(TWITTER_RETROFIT)
            retrofit.create(TwitterService::class.java)
        }

        bind<TwitterCredentialsAuthInterceptor>() with singleton {
            TwitterCredentialsAuthInterceptor(
                    twitterConsumerKey = BuildConfig.TWITTER_CONSUMER_KEY,
                    twitterConsumerSecret = BuildConfig.TWITTER_CONSUMER_SECRET
            )
        }

        bind<OkHttpClient>(TWITTER_AUTH_OKHTTP_CLIENT) with singleton {
            val builder = OkHttpClient.Builder()

            val interceptor: TwitterCredentialsAuthInterceptor = instance()
            builder.addInterceptor(interceptor)

            val cache: Cache = instance()
            builder.cache(cache)

            builder.build()
        }

        bind<Retrofit>(TWITTER_AUTH_RETROFIT) with singleton {
            Retrofit.Builder()
                    .baseUrl(TWITTER_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(instance(TWITTER_AUTH_OKHTTP_CLIENT))
                    .build()

        }

        bind<TwitterAuthService>() with singleton {
            val retrofit: Retrofit = instance(TWITTER_AUTH_RETROFIT)
            retrofit.create(TwitterAuthService::class.java)
        }
    }
}