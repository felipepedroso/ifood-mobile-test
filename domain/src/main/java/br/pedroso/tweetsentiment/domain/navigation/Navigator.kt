package br.pedroso.tweetsentiment.domain.navigation

interface Navigator {
    fun openHome()
    fun openTweetsList()
    fun openTweetsListWithHomeOnBackstack()
}
