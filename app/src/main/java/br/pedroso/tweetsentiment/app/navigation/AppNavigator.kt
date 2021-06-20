package br.pedroso.tweetsentiment.app.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import br.pedroso.tweetsentiment.app.features.home.HomeActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.domain.navigation.Navigator

class AppNavigator(private val context: Context) : Navigator {
    override fun openHome() {
        context.startActivity(Intent(context, HomeActivity::class.java))
    }

    private fun createTweetsListIntent(context: Context) =
        Intent(context, TweetsListActivity::class.java)

    override fun openTweetsList() {
        context.startActivity(createTweetsListIntent(context))
    }

    override fun openTweetsListWithHomeOnBackstack() {
        context.startActivities(
            arrayOf(
                HomeActivity.createIntent(context),
                createTweetsListIntent(context)
            )
        )
    }
}
