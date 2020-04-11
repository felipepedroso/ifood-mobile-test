package br.pedroso.tweetsentiment.presentation.features.tweetsList

sealed class TweetsListSingleEvent {
    object DisplayErrorDuringAnalysisMessage : TweetsListSingleEvent()
    object DisplaySelectOtherUserConfirmation : TweetsListSingleEvent()
    object CloseScreen : TweetsListSingleEvent()
}
