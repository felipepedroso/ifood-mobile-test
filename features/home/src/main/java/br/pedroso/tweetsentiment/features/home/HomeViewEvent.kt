package br.pedroso.tweetsentiment.features.home

sealed class HomeViewEvent {
    class SubmitTwitterUsername(val username: String) : HomeViewEvent()
    class ChangedUsername(val username: String) : HomeViewEvent()
}
