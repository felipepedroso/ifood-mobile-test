package br.pedroso.tweetsentiment.app.di.device.storage.filesystem

import android.content.Context
import br.pedroso.tweetsentiment.app.di.DependenciesTags.Companion.NETWORK_CACHE_DIR
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import java.io.File

/**
 * Created by felip on 10/03/2018.
 */
class CacheDirModule(private val context: Context) {
    val graph = Kodein.Module {
        bind<File>(NETWORK_CACHE_DIR) with singleton {
            context.cacheDir
        }
    }
}