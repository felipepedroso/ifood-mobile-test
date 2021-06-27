package br.pedroso.tweetsentiment.features.tweetslist

sealed class TweetsListViewState {
    object LoadingContent : TweetsListViewState()
    object RunningAnalysis : TweetsListViewState()
    object Ready : TweetsListViewState()
    class Error(val error: Throwable) : TweetsListViewState()
}
