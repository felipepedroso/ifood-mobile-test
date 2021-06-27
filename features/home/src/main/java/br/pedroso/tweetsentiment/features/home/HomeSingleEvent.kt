package br.pedroso.tweetsentiment.features.home

sealed class HomeSingleEvent {
    object NavigateToTweetsListScreen : HomeSingleEvent()
}
