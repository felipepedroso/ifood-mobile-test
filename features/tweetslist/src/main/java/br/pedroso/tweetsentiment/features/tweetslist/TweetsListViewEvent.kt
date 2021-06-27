package br.pedroso.tweetsentiment.features.tweetslist

import br.pedroso.tweetsentiment.domain.entities.Tweet

sealed class TweetsListViewEvent {
    object RequestedRefresh : TweetsListViewEvent()
    data class AnalyseTweet(val tweet: Tweet) : TweetsListViewEvent()
    object PressedBackButton : TweetsListViewEvent()
    object RequestedToSelectOtherUser : TweetsListViewEvent()
    object SelectOtherUserConfirmed : TweetsListViewEvent()
}
