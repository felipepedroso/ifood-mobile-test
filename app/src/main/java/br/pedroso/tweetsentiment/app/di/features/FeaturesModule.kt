package br.pedroso.tweetsentiment.app.di.features

import br.pedroso.tweetsentiment.app.di.features.home.homeModule
import br.pedroso.tweetsentiment.app.di.features.shared.sharedModule
import br.pedroso.tweetsentiment.app.di.features.splash.splashModule
import br.pedroso.tweetsentiment.app.di.features.tweetslist.tweetsListModule

val featuresModules = listOf(
    splashModule,
    sharedModule,
    homeModule,
    tweetsListModule
)
