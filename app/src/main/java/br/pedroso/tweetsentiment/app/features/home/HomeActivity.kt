package br.pedroso.tweetsentiment.app.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.app.utils.viewModelProvider
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.domain.errors.UiError
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewModel
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_loading_feedback.*
import timber.log.Timber

@Suppress("IMPLICIT_CAST_TO_ANY")
class HomeActivity : BaseActivity() {
    private val kodein by lazy { LazyKodein(appKodein) }
    private val homeViewModel by viewModelProvider { kodein.value.instance<HomeViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

    override fun onBackPressed() = Unit

    private fun setupView() {
        setContentView(R.layout.activity_home)

        buttonCheckUserTweets.setOnClickListener {
            clickedCheckUserTweets()
        }

        setupEditTextTwitterAccount()
    }

    private fun setupEditTextTwitterAccount() {
        editTextTwitterAccount.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    clickedCheckUserTweets()
                    hideSoftInputWindow()
                    true
                }
                else -> false
            }
        }

        editTextTwitterAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when (s.toString().trim().length) {
                    0 -> disableContinueButton()
                    else -> enableContinueButton()
                }
                resetErrorMessageState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun clickedCheckUserTweets() {
        val username = editTextTwitterAccount.text.toString()

        val subscription =
                homeViewModel.syncNewUser(username)
                        .doOnNext { handleNewViewState(it) }
                        .doOnError { logUnregisteredException(it) }
                        .subscribe()

        registerDisposable(subscription)
    }

    private fun logUnregisteredException(throwable: Throwable?) = Timber.e(throwable)

    private fun handleNewViewState(viewState: ViewState) = when (viewState) {
        is ViewState.Loading -> showLoading()
        is ViewState.Success -> finishedLoadingUser()
        is ViewState.Error -> displayError(viewState.error)
        else -> Timber.d("State not used: $viewState")
    }

    private fun displayError(error: Throwable) {
        hideLoading()

        when (error) {
            is UiError.EmptyField -> showEmptyUsernameErrorMessage()
            is TwitterError.UserNotFound -> showUserNotFoundState()
            else -> showErrorState(error)
        }
    }

    private fun finishedLoadingUser() {
        hideLoading()
        clearUsernameTextView()
        openTweetListScreen()
    }

    private fun clearUsernameTextView() {
        editTextTwitterAccount.text.clear()
    }

    private fun enableContinueButton() {
        buttonCheckUserTweets.isEnabled = true
    }

    private fun disableContinueButton() {
        buttonCheckUserTweets.isEnabled = false
    }

    private fun showEmptyUsernameErrorMessage() {
        textInputLayoutTwitterAccount.error = getString(R.string.error_empty_twitter_account)
        textInputLayoutTwitterAccount.isErrorEnabled = true
    }

    private fun showErrorState(error: Throwable) = displayErrorMessage(R.string.error_generic)

    private fun showLoading() {
        resetErrorMessageState()
        loadingHolder.visibility = View.VISIBLE
        disableControls()
    }

    private fun showUserNotFoundState() = displayErrorMessage(R.string.error_user_not_found)

    private fun displayErrorMessage(messageResourceId: Int) {
        textViewErrorMessage.text = getString(messageResourceId)
        textViewErrorMessage.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingHolder.visibility = View.GONE
        enableControls()
    }

    private fun resetErrorMessageState() {
        textViewErrorMessage.text = ""
        textViewErrorMessage.visibility = View.GONE
    }

    private fun openTweetListScreen() {
        TweetsListActivity.navigateHere(this)
    }

    companion object {
        fun navigateHere(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
