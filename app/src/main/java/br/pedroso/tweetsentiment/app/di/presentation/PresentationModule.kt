package br.pedroso.tweetsentiment.app.di.presentation

import br.pedroso.tweetsentiment.app.di.presentation.features.HomeModule
import br.pedroso.tweetsentiment.app.di.presentation.features.SplashModule
import br.pedroso.tweetsentiment.app.di.presentation.features.TweetsListModule
import br.pedroso.tweetsentiment.presentation.shared.usecases.di.CommonUsecasesModule
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felip on 08/03/2018.
 */
class PresentationModule {
    val graph = Kodein.Module {
        import(CommonUsecasesModule().graph)
        import(HomeModule().graph)
        import(SplashModule().graph)
        import(TweetsListModule().graph)
    }
}