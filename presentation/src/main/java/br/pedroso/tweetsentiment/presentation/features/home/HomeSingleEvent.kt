package br.pedroso.tweetsentiment.presentation.features.home

sealed class HomeSingleEvent {
    object NavigateToTweetsListScreen : HomeSingleEvent()
}
