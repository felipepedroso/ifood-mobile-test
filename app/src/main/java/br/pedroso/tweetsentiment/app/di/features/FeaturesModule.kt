package br.pedroso.tweetsentiment.app.di.features

import br.pedroso.tweetsentiment.features.home.di.homeModule
import br.pedroso.tweetsentiment.app.di.features.shared.sharedModule
import br.pedroso.tweetsentiment.features.splash.di.splashModule
import br.pedroso.tweetsentiment.features.tweetslist.di.tweetsListModule

val featuresModules = listOf(
    splashModule,
    sharedModule,
    homeModule,
    tweetsListModule
)
