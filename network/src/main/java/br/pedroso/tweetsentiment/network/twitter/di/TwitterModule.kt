package br.pedroso.tweetsentiment.network.twitter.di

import br.pedroso.tweetsentiment.domain.network.dataSources.TwitterDataSource
import br.pedroso.tweetsentiment.network.twitter.retrofit.RetrofitTwitterDataSource
import br.pedroso.tweetsentiment.network.twitter.retrofit.di.TwitterRetrofitModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton

/**
 * Created by felip on 09/03/2018.
 */
class TwitterModule {
    val graph = Kodein.Module {
        import(TwitterRetrofitModule().graph)

        bind<TwitterDataSource>() with singleton {
            RetrofitTwitterDataSource(
                    twitterService = instance(),
                    twitterAuthService = instance(),
                    applicationPreferences = instance()
            )
        }
    }
}