package br.pedroso.tweetsentiment.app.features.tweetsList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.features.home.HomeActivity

class TweetsListActivity : AppCompatActivity(R.layout.activity_tweets_list) {

    companion object {
        private fun createIntent(context: Context) = Intent(context, TweetsListActivity::class.java)

        fun navigateHere(context: Context) {
            context.startActivity(createIntent(context))
        }

        fun navigateHereWithHomeAtBackstack(context: Context) {
            context.startActivities(
                arrayOf(
                    HomeActivity.createIntent(context),
                    createIntent(context)
                )
            )
        }
    }
}
