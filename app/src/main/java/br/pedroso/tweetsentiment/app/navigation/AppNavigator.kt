package br.pedroso.tweetsentiment.app.navigation

import android.content.Context
import android.content.Intent
import br.pedroso.tweetsentiment.features.tweetslist.TweetsListActivity
import br.pedroso.tweetsentiment.domain.navigation.Navigator
import br.pedroso.tweetsentiment.features.home.HomeActivity

class AppNavigator(private val context: Context) : Navigator {

    private fun createHomeIntent() = Intent(context, HomeActivity::class.java)

    override fun openHome() {
        context.startActivity(createHomeIntent())
    }

    private fun createTweetsListIntent(context: Context) =
        Intent(context, br.pedroso.tweetsentiment.features.tweetslist.TweetsListActivity::class.java)

    override fun openTweetsList() {
        context.startActivity(createTweetsListIntent(context))
    }

    override fun openTweetsListWithHomeOnBackstack() {
        context.startActivities(
            arrayOf(
                createHomeIntent(),
                createTweetsListIntent(context)
            )
        )
    }
}
