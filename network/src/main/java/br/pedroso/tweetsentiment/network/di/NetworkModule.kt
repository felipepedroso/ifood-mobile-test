package br.pedroso.tweetsentiment.network.di

import br.pedroso.tweetsentiment.network.common.di.NetworkCommonModule
import br.pedroso.tweetsentiment.network.twitter.di.TwitterModule
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felip on 08/03/2018.
 */
class NetworkModule {
    val graph = Kodein.Module {
        import(NetworkCommonModule().graph)
        import(TwitterModule().graph)
    }
}