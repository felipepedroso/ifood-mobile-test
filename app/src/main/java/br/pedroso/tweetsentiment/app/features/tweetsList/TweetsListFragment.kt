package br.pedroso.tweetsentiment.app.features.tweetsList

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.extensions.disableControls
import br.pedroso.tweetsentiment.app.extensions.enableControls
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.domain.entities.User
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListSingleEvent
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListViewEvent
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListViewModel
import br.pedroso.tweetsentiment.presentation.features.tweetsList.TweetsListViewState
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_tweets_list_content.collapsingToolbarLayoutTweetsList
import kotlinx.android.synthetic.main.layout_tweets_list_content.coordinatorLayoutTweetsListContent
import kotlinx.android.synthetic.main.layout_tweets_list_content.imageViewProfileBackground
import kotlinx.android.synthetic.main.layout_tweets_list_content.imageViewProfilePicture
import kotlinx.android.synthetic.main.layout_tweets_list_content.recyclerViewTweetsList
import kotlinx.android.synthetic.main.layout_tweets_list_content.swipeRefreshLayoutRoot
import kotlinx.android.synthetic.main.layout_tweets_list_content.toolbarTweetsList
import kotlinx.android.synthetic.main.view_error_feedback.buttonGoBack
import kotlinx.android.synthetic.main.view_error_feedback.imageViewError
import kotlinx.android.synthetic.main.view_error_feedback.linearLayoutErrorContainer
import kotlinx.android.synthetic.main.view_error_feedback.textViewErrorMessage
import kotlinx.android.synthetic.main.view_loading_feedback.loadingHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class TweetsListFragment : Fragment(R.layout.fragment_tweets_list) {

    private val viewModel: TweetsListViewModel by viewModel()

    private val tweetsListAdapter = TweetsListAdapter { tweet ->
        viewModel.onViewEvent(TweetsListViewEvent.AnalyseTweet(tweet))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewState()
        observeSingleEvents()
        observeUserState()
        observeTweetsListState()
        setupBackDispatcher()
    }

    private fun setupBackDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.onViewEvent(TweetsListViewEvent.PressedBackButton)
        }
    }

    private fun setupView() {
        setupTweetsList()
        setupToolbar()
        setupSwipeRefreshLayout()
        setupGoBackButton()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayoutRoot.setOnRefreshListener {
            viewModel.onViewEvent(TweetsListViewEvent.RequestedRefresh)
        }
    }

    private fun setupGoBackButton() {
        buttonGoBack.setOnClickListener {
            viewModel.onViewEvent(TweetsListViewEvent.PressedBackButton)
        }
    }

    private fun setupTweetsList() {
        val manager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())

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
                    viewModel.onViewEvent(TweetsListViewEvent.RequestedToSelectOtherUser)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                TweetsListViewState.LoadingContent -> displayLoadingContent()
                TweetsListViewState.RunningAnalysis -> displayRunningAnalysis()
                TweetsListViewState.Ready -> displayReadyState()
                is TweetsListViewState.Error -> showErrorState(state.error)
            }
        })
    }

    private fun observeSingleEvents() {
        viewModel.singleEvents.observe(viewLifecycleOwner, Observer { event ->
            when (event) {
                TweetsListSingleEvent.DisplayErrorDuringAnalysisMessage ->
                    displayErrorDuringAnalysisMessage()
                TweetsListSingleEvent.DisplaySelectOtherUserConfirmation ->
                    displaySelectOtherUserConfirmation()
                TweetsListSingleEvent.CloseScreen -> closeScreen()
            }
        })
    }

    private fun observeUserState() {
        viewModel.userState.observe(viewLifecycleOwner, Observer { user -> showUserProfile(user) })
    }

    private fun observeTweetsListState() {
        viewModel.tweetsListState.observe(viewLifecycleOwner, Observer { tweetsList ->
            displayTweetsList(tweetsList)
        })
    }

    private fun displayReadyState() {
        resetErrorContainerState()
        hideLoadingContent()
        hideRunningAnalysis()
    }

    private fun showUserProfile(user: User) {
        collapsingToolbarLayoutTweetsList.title = user.name

        Picasso.get().load(user.profilePictureUrl).into(imageViewProfilePicture)

        if (!TextUtils.isEmpty(user.bannerUrl)) {
            Picasso.get().load(user.bannerUrl).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageViewProfileBackground, object : Callback {
                    override fun onError(e: Exception?) = Unit

                    override fun onSuccess() {
                        val bitmap = (imageViewProfileBackground.drawable as BitmapDrawable).bitmap
                        Palette.from(bitmap)
                            .generate { palette -> palette?.run { applyPalette(this) } }
                    }
                })
        }
    }

    private fun applyPalette(palette: Palette) {
        val primaryDark = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
        val primary = ContextCompat.getColor(requireContext(), R.color.colorPrimary)

        val darkMutedColor = palette.getMutedColor(primary)
        collapsingToolbarLayoutTweetsList.setContentScrimColor(darkMutedColor)
        imageViewProfileBackground.drawable.colorFilter =
            PorterDuffColorFilter(darkMutedColor, PorterDuff.Mode.MULTIPLY)
        collapsingToolbarLayoutTweetsList.setStatusBarScrimColor(
            palette.getDarkMutedColor(
                primaryDark
            )
        )
        imageViewProfilePicture.borderColor = darkMutedColor
        requireActivity().supportStartPostponedEnterTransition()
    }

    private fun displayTweetsList(tweetsList: List<Tweet>) =
        tweetsListAdapter.submitList(tweetsList)

    private fun displayLoadingContent() {
        swipeRefreshLayoutRoot.isRefreshing = true
        requireActivity().disableControls()
    }

    private fun hideLoadingContent() {
        swipeRefreshLayoutRoot.isRefreshing = false
        requireActivity().enableControls()
    }

    private fun showErrorState(it: Throwable) = when (it) {
        is TwitterError.EmptyResponse -> showEmptyState()
        is TwitterError.UserNotFound -> showUserNotFoundState()
        else -> showErrorFeedback(R.drawable.ic_feedback_sad, R.string.error_loading_timeline)
    }

    private fun showEmptyState() =
        showErrorFeedback(R.drawable.ic_feedback_neutral, R.string.empty_tweet_timeline)

    private fun showUserNotFoundState() =
        showErrorFeedback(R.drawable.ic_feedback_sad, R.string.error_user_not_found)

    private fun showErrorFeedback(errorImageResource: Int, errorStringResource: Int) {
        hideLoadingContent()
        hideRunningAnalysis()
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

    private fun displayRunningAnalysis() {
        loadingHolder.visibility = View.VISIBLE
        requireActivity().disableControls()
    }

    private fun hideRunningAnalysis() {
        loadingHolder.visibility = View.GONE
        requireActivity().enableControls()
    }

    private fun displaySelectOtherUserConfirmation() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder
            .setTitle(R.string.select_other_user)
            .setMessage(R.string.select_other_user_dialog_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.onViewEvent(TweetsListViewEvent.SelectOtherUserConfirmed)
            }
            .setNegativeButton(R.string.cancel, null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun displayErrorDuringAnalysisMessage() {
        hideRunningAnalysis()
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder
            .setTitle(R.string.natural_language_api_error_title)
            .setMessage(R.string.natural_language_api_error_message)
            .setPositiveButton(R.string.ok, null)

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun closeScreen() {
        requireActivity().finish()
    }
}
