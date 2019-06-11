package br.pedroso.tweetsentiment.app.di.network

import br.pedroso.tweetsentiment.app.di.DependenciesTags
import br.pedroso.tweetsentiment.app.di.network.naturalLanguage.NaturalLanguageApiModule
import br.pedroso.tweetsentiment.app.di.network.twitter.TwitterModule
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import okhttp3.Cache
import java.io.File

class NetworkModule {
    val graph = Kodein.Module {
        bind<Cache>() with singleton {
            val cacheDir: File = instance(DependenciesTags.NETWORK_CACHE_DIR)
            Cache(cacheDir, NETWORK_CACHE_SIZE)
        }

        import(TwitterModule().graph)
        import(NaturalLanguageApiModule().graph)
    }

    companion object {
        private const val NETWORK_CACHE_SIZE = 10L * 1024 * 1024
    }
}
