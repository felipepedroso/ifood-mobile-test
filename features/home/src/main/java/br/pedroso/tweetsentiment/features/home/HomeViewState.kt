package br.pedroso.tweetsentiment.features.home

sealed class HomeViewState {
    class Idle(
        val isContinueButtonEnabled: Boolean,
        val shouldDisplayEmptyUsernameError: Boolean
    ) : HomeViewState()

    object Loading : HomeViewState()
    class Error(val error: Throwable) : HomeViewState()
}
