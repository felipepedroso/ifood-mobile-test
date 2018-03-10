package br.pedroso.tweetsentiment.network.common.di

import br.pedroso.tweetsentiment.domain.di.DependenciesTags.Companion.NETWORK_CACHE_DIR
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import okhttp3.Cache
import java.io.File

/**
 * Created by felip on 10/03/2018.
 */
class NetworkCommonModule {
    val graph = Kodein.Module {
        bind<Cache>() with singleton {
            val cacheDir: File = instance(NETWORK_CACHE_DIR)
            Cache(cacheDir, NETWORK_CACHE_SIZE)
        }
    }

    companion object {
        private const val NETWORK_CACHE_SIZE = 10L * 1024 * 1024
    }
}