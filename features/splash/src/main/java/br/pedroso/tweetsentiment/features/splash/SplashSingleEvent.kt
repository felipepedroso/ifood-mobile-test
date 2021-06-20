package br.pedroso.tweetsentiment.features.splash

sealed class SplashSingleEvent {
    object NavigateToHomeScreen : SplashSingleEvent()
    object NavigateToTweetsListScreen : SplashSingleEvent()
}