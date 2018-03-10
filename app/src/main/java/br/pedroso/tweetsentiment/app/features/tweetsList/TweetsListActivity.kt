package br.pedroso.tweetsentiment.app.features.tweetsList

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.features.home.HomeActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.utils.ToolbarAnimationCoordinator
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListPresenter
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_tweets_list.*
import kotlinx.android.synthetic.main.view_error_feedback.*

class TweetsListActivity : AppCompatActivity(), TweetsListView {

    private val kodein by lazy { LazyKodein(appKodein) }
    val presenter by kodein.with(this).instance<TweetsListPresenter>()

    private val tweetsListAdapter = TweetsListAdapter {
        presenter.clickedOnAnalyzeTweet(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    private fun setupView() {
        setContentView(R.layout.activity_tweets_list)

        setupTweetsList()

        setupToolbar()
    }

    private fun setupTweetsList() {
        var manager = LinearLayoutManager(this)

        recyclerViewTweetsList.apply {
            layoutManager = manager
            adapter = tweetsListAdapter
            setHasFixedSize(true)
        }
    }


    private fun setupToolbar() {
        toolbarTweetsList.inflateMenu(R.menu.menu_tweets_list_activity)
        toolbarTweetsList.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_select_other_user -> {
                    presenter.clickedOnSelectOtherUser()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }

        val toolbarAnimationCoordinator = ToolbarAnimationCoordinator(imageViewProfilePicture, textViewName, collapsingToolbarLayoutTweetsList, linearLayoutUserDetailsContainer)
        appBarLayoutTweetsList.addOnOffsetChangedListener(toolbarAnimationCoordinator)
    }

    override fun onResume() {
        super.onResume()
        presenter.executeFirstSync()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.releaseSubscriptions()
    }

    private fun showErrorFeedback(errorImageResource: Int, errorStringResource: Int) {
        linearLayoutErrorContainer.visibility = View.VISIBLE
        imageViewError.setImageResource(errorImageResource)
        textViewErrorMessage.text = getString(errorStringResource)
    }

    private fun resetErrorContainerState() = Action {
        linearLayoutErrorContainer.visibility = View.GONE
        textViewErrorMessage.text = ""
        imageViewError.setImageResource(0)
    }

    override fun showEmptyState() = Action {
        showErrorFeedback(R.drawable.ic_error_sad, R.string.empty_tweet_timeline)
    }

    override fun showErrorState(it: Throwable) = Action {
        showErrorFeedback(R.drawable.ic_error_sad, R.string.error_loading_timeline)
    }

    override fun showLoading() = Action {
        progressBarLoadingTweets.visibility = View.VISIBLE
    }

    override fun showUserNotFoundState() = Action {
        showErrorFeedback(R.drawable.ic_error_sad, R.string.error_user_not_found)
    }

    override fun hideLoading() = Action {
        progressBarLoadingTweets.visibility = View.GONE
    }

    override fun hideEmptyState() = resetErrorContainerState()

    override fun hideErrorState() = resetErrorContainerState()

    override fun hideUserNotFoundState() = resetErrorContainerState()

    override fun showUserProfile(user: User) = Action {
        textViewUsername.text = "@" + user.userName
        textViewName.text = user.name

        Picasso.with(this).load(user.profilePictureUrl).into(imageViewProfilePicture)
        if (!TextUtils.isEmpty(user.bannerUrl)) {
            Picasso.with(this).load(user.bannerUrl).into(imageViewProfileBackground, object : Callback {
                override fun onSuccess() {
                    val bitmap = (imageViewProfileBackground.drawable as BitmapDrawable).bitmap
                    Palette.from(bitmap).generate { palette -> applyPalette(palette) }
                }

                override fun onError() {
                }
            })
        }
    }

    private fun applyPalette(palette: Palette) {
        val primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        val primary = ContextCompat.getColor(this, R.color.colorPrimary)

        val darkMutedColor = palette.getMutedColor(primary)
        collapsingToolbarLayoutTweetsList.setContentScrimColor(darkMutedColor)

        collapsingToolbarLayoutTweetsList.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark))
        supportStartPostponedEnterTransition()
    }

    override fun showTweet(tweet: Tweet) = Action {
        tweetsListAdapter.addTweed(tweet)
    }

    override fun displayAnalyzingTweetScreen(tweet: Tweet) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun askConfirmationToSelectOtherUser() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder
                .setTitle(R.string.select_other_user)
                .setMessage(R.string.select_other_user_dialog_message)
                .setPositiveButton(R.string.ok, { _, _ -> presenter.confirmedToChangeUser() })
                .setNegativeButton(R.string.cancel, null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun navigateToApplicationHome() {
        HomeActivity.navigateHere(this)
    }

    companion object {
        fun navigateHere(context: Context) {
            val intent = Intent(context, TweetsListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
