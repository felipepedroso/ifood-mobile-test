package br.pedroso.tweetsentiment.presentation.features.splash

sealed class SplashSingleEvent {
    object NavigateToHomeScreen : SplashSingleEvent()
    object NavigateToTweetsListScreen : SplashSingleEvent()
}