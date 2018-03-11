package br.pedroso.tweetsentiment.presentation.di

import br.pedroso.tweetsentiment.presentation.features.backgroundSync.di.BackgroundSyncModule
import br.pedroso.tweetsentiment.presentation.features.home.di.HomeModule
import br.pedroso.tweetsentiment.presentation.features.tweetsList.di.TweetsListModule
import br.pedroso.tweetsentiment.presentation.shared.usecases.di.CommonUsecasesModule
import com.github.salomonbrys.kodein.Kodein

/**
 * Created by felip on 08/03/2018.
 */
class PresentationModule {
    val graph = Kodein.Module {
        import(CommonUsecasesModule().graph)
        import(HomeModule().graph)
        import(TweetsListModule().graph)
        import(BackgroundSyncModule().graph)
    }
}