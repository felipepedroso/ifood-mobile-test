package br.pedroso.tweetsentiment.features.tweetslist

sealed class TweetsListSingleEvent {
    object DisplayErrorDuringAnalysisMessage : TweetsListSingleEvent()
    object DisplaySelectOtherUserConfirmation : TweetsListSingleEvent()
    object CloseScreen : TweetsListSingleEvent()
}
