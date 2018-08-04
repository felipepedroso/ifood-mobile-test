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
import br.pedroso.tweetsentiment.app.utils.showToast
import br.pedroso.tweetsentiment.app.utils.viewModelProvider
import br.pedroso.tweetsentiment.domain.Tweet
import br.pedroso.tweetsentiment.domain.User
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListViewModel
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tweets_list_content.*
import kotlinx.android.synthetic.main.view_error_feedback.*
import kotlinx.android.synthetic.main.view_loading_feedback.*
import timber.log.Timber

class TweetsListActivity : BaseActivity() {

    private val kodein by lazy { LazyKodein(appKodein) }
    private val tweetsListViewModel by viewModelProvider { kodein.value.instance<TweetsListViewModel>() }
    private val tweetsListAdapter = TweetsListAdapter(this::clickedOnAnalyzeTweet)

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
            confirmedToChangeUser()
        }
    }

    override fun onBackPressed() {
        if (linearLayoutErrorContainer.visibility == View.VISIBLE) {
            confirmedToChangeUser()
        } else {
            askConfirmationToSelectOtherUser()
        }
    }

    private fun setupTweetsList() {
        val manager = LinearLayoutManager(this)

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
                    askConfirmationToSelectOtherUser()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        syncUserData()
    }

    private fun syncUserData() {
        val subscription = tweetsListViewModel
                .syncUserData()
                .doOnSubscribe { showLoading() }
                .doOnTerminate { hideLoading() }
                .subscribe(
                        { completedUserDataSync() },
                        { showErrorState(it) }
                )

        registerDisposable(subscription)
    }

    private fun completedUserDataSync() {
        resetErrorContainerState()
        getCurrentUser()
        //getTweetsFromCurrentUser()
    }

    private fun getCurrentUser() {
        val subscription = tweetsListViewModel
                .getCurrentUser()
                .subscribe(
                        { handleGetCurrentUserNewState(it) },
                        { Timber.e(it) }
                )

        registerDisposable(subscription)
    }

    private fun handleGetCurrentUserNewState(viewState: ViewState) = when (viewState) {
        is ViewState.Loading -> showLoading()
        is ViewState.ShowContent<*> -> showUserProfile(viewState.contentValue as User)
        is ViewState.Error -> showErrorState(viewState.error)
        else -> Timber.d("State not used: $viewState")
    }

    private fun getTweetsFromCurrentUser() {
        val subscription = tweetsListViewModel
                .getTweetsFromCurrentUser()
                .subscribe(
                        { handleGetTweetsFromCurrentUserNewState(it) },
                        { Timber.e(it) }
                )

        registerDisposable(subscription)
    }

    private fun handleGetTweetsFromCurrentUserNewState(viewState: ViewState) = when (viewState) {
        is ViewState.Loading -> showLoading()
        is ViewState.ShowContent<*> -> displayTweetsList(viewState.contentValue as List<Tweet>)
        is ViewState.Error -> showErrorState(viewState.error)
        else -> Timber.d("State not used: $viewState")
    }

    private fun displayTweetsList(tweetsList: List<Tweet>) {
        tweetsListAdapter.setTweetsList(tweetsList)
    }

    private fun handleScheduleRecurrentSyncNewState(viewState: ViewState) = when (viewState) {
        is ViewState.ShowContent<*> -> displayRecurrentSyncSuccessMessage(viewState.contentValue as Int)
        is ViewState.Error -> showToast(R.string.failed_to_schedule_recurrent_sync)
        else -> Timber.d("State not used: $viewState")
    }

    private fun displayRecurrentSyncSuccessMessage(recurrentSyncTimeInterval: Int) {
        val message = getString(R.string.successful_recurrent_sync_schedule, recurrentSyncTimeInterval)
        showToast(message)
    }

    private fun clickedOnAnalyzeTweet(tweet: Tweet) {
        val subscription = tweetsListViewModel
                .analyzeTweet(tweet)
                .subscribe(
                        { handleAnalyzeTweetNewState(it) },
                        { Timber.e(it) }
                )

        registerDisposable(subscription)
    }

    private fun handleAnalyzeTweetNewState(viewState: ViewState) = when (viewState) {
        is ViewState.Loading -> showLoading()
        is ViewState.Success -> displayAnalyzeTweetSuccess()
        is ViewState.Error -> showAnalyzeTweetErrorMessage()
        else -> Timber.d("State not used: $viewState")
    }

    private fun displayAnalyzeTweetSuccess() {

    }

    private fun showErrorFeedback(errorImageResource: Int, errorStringResource: Int) {
        linearLayoutErrorContainer.visibility = View.VISIBLE
        imageViewError.setImageResource(errorImageResource)
        textViewErrorMessage.text = getString(errorStringResource)
        coordinatorLayoutTweetsListContent.visibility = View.GONE
    }

    private fun resetErrorContainerState() {
        linearLayoutErrorContainer.visibility = View.GONE
        textViewErrorMessage.text = ""
        imageViewError.setImageResource(0)
        coordinatorLayoutTweetsListContent.visibility = View.VISIBLE
    }

    fun showEmptyState() {
        showErrorFeedback(R.drawable.ic_feedback_neutral, R.string.empty_tweet_timeline)
    }

    private fun showErrorState(it: Throwable) {
        showErrorFeedback(R.drawable.ic_feedback_sad, R.string.error_loading_timeline)
    }

    private fun showLoading() {
        loadingHolder.visibility = View.VISIBLE
        disableComponents()
    }

    private fun disableComponents() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun showUserNotFoundState() {
        showErrorFeedback(R.drawable.ic_feedback_sad, R.string.error_user_not_found)
    }

    private fun hideLoading() {
        loadingHolder.visibility = View.GONE
        enableComponents()
    }

    private fun enableComponents() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideEmptyState() = resetErrorContainerState()

    fun hideErrorState() = resetErrorContainerState()

    fun hideUserNotFoundState() = resetErrorContainerState()

    private fun showUserProfile(user: User) {
        hideLoading()

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

    private fun askConfirmationToSelectOtherUser() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder
                .setTitle(R.string.select_other_user)
                .setMessage(R.string.select_other_user_dialog_message)
                .setPositiveButton(R.string.ok, { _, _ -> confirmedToChangeUser() })
                .setNegativeButton(R.string.cancel, null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun confirmedToChangeUser() {
        val subscription = tweetsListViewModel
                .clearCurrentUserSettings()
                .doOnComplete { navigateToApplicationHome() }
                .doOnError { Timber.e(it) }
                .subscribe()

        registerDisposable(subscription)
    }

    private fun showAnalyzeTweetErrorMessage() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder
                .setTitle(R.string.natural_language_api_error_title)
                .setMessage(R.string.natural_language_api_error_message)
                .setPositiveButton(R.string.ok, null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun navigateToApplicationHome() {
        finish()
    }

    companion object {
        fun navigateHere(context: Context) {
            val intent = Intent(context, TweetsListActivity::class.java)
            context.startActivity(intent)
        }
    }
}
