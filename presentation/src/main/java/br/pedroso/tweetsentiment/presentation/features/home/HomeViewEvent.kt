package br.pedroso.tweetsentiment.presentation.features.home

sealed class HomeViewEvent {
    class SubmitTwitterUsername(val username: String) : HomeViewEvent()
    class ChangedUsername(val username: String) : HomeViewEvent()
}
