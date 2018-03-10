package br.pedroso.tweetsentiment.app.features.tweetsList

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListPresenter
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import io.reactivex.functions.Action

class TweetsListActivity : AppCompatActivity(), TweetsListView {

    private val kodein by lazy { LazyKodein(appKodein) }
    val presenter by kodein.with(this).instance<TweetsListPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets_list)
    }

    override fun onResume() {
        super.onResume()
        presenter.executeFirstSync()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.releaseSubscriptions()
    }

    override fun showEmptyState(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorState(it: Throwable): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserNotFoundState(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideEmptyState(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideErrorState(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideUserNotFoundState(): Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showUserProfile(user: User) = Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTweet(tweet: Tweet) = Action {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun displayAnalyzingTweetScreen(tweet: Tweet) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun askConfirmationToSelectOtherUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToApplicationHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun navigateHere(context: Context) {
            val intent = Intent(context, TweetsListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
