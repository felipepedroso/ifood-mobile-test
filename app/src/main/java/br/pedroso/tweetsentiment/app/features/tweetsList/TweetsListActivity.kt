package br.pedroso.tweetsentiment.app.features.tweetsList

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListPresenter
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_tweets_list_content.*
import kotlinx.android.synthetic.main.view_error_feedback.*
import kotlinx.android.synthetic.main.view_loading_feedback.*

class TweetsListActivity : BaseActivity(), TweetsListView {


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

        setupGoBackButton()
    }

    private fun setupGoBackButton() {
        buttonGoBack.setOnClickListener {
            presenter.confirmedToChangeUser()
        }
    }

    override fun onBackPressed() {
        if (linearLayoutErrorContainer.visibility == View.VISIBLE) {
            presenter.confirmedToChangeUser()
        } else {
            presenter.clickedOnSelectOtherUser()
        }
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
        coordinatorLayoutTweetsListContent.visibility = View.GONE
    }

    private fun resetErrorContainerState() = Action {
        linearLayoutErrorContainer.visibility = View.GONE
        textViewErrorMessage.text = ""
        imageViewError.setImageResource(0)
        coordinatorLayoutTweetsListContent.visibility = View.VISIBLE
    }

    override fun showEmptyState() = Action {
        showErrorFeedback(R.drawable.ic_feedback_neutral, R.string.empty_tweet_timeline)
    }

    override fun showErrorState(it: Throwable) = Action {
        showErrorFeedback(R.drawable.ic_feedback_sad, R.string.error_loading_timeline)
    }

    override fun showLoading() = Action {
        loadingHolder.visibility = View.VISIBLE
        disableComponents()
    }

    private fun disableComponents() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun showUserNotFoundState() = Action {
        showErrorFeedback(R.drawable.ic_feedback_sad, R.string.error_user_not_found)
    }

    override fun hideLoading() = Action {
        loadingHolder.visibility = View.GONE
        enableComponents()
    }

    private fun enableComponents() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun hideEmptyState() = resetErrorContainerState()

    override fun hideErrorState() = resetErrorContainerState()

    override fun hideUserNotFoundState() = resetErrorContainerState()

    override fun showUserProfile(user: User) = Action {
        collapsingToolbarLayoutTweetsList.title = user.name


        Picasso.with(this).load(user.profilePictureUrl).into(imageViewProfilePicture)
        if (!TextUtils.isEmpty(user.bannerUrl)) {
            Picasso.with(this).load(user.bannerUrl).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageViewProfileBackground, object : Callback {
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
        imageViewProfileBackground.drawable.colorFilter = PorterDuffColorFilter(darkMutedColor, PorterDuff.Mode.MULTIPLY)
        collapsingToolbarLayoutTweetsList.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark))
        imageViewProfilePicture.borderColor = darkMutedColor
        supportStartPostponedEnterTransition()
    }

    override fun showTweet(tweet: Tweet) = Action {
        tweetsListAdapter.addTweed(tweet)
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

    override fun showNaturalLanguageApiError() = Action {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder
                .setTitle(R.string.natural_language_api_error_title)
                .setMessage(R.string.natural_language_api_error_message)
                .setPositiveButton(R.string.ok, null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    override fun navigateToApplicationHome() {
        finish()
    }

    companion object {
        fun navigateHere(context: Context) {
            val intent = Intent(context, TweetsListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
