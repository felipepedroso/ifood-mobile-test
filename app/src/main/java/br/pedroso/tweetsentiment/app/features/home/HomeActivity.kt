package br.pedroso.tweetsentiment.app.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.domain.errors.UiError
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.presentation.features.home.HomeSingleEvent
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewEvent.ChangedUsername
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewEvent.SubmitTwitterUsername
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewModel
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewState
import kotlinx.android.synthetic.main.activity_home.buttonCheckUserTweets
import kotlinx.android.synthetic.main.activity_home.editTextTwitterAccount
import kotlinx.android.synthetic.main.activity_home.textInputLayoutTwitterAccount
import kotlinx.android.synthetic.main.activity_home.textViewErrorMessage
import kotlinx.android.synthetic.main.view_loading_feedback.loadingHolder
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        observeViewState()
        observeSingleEvents()
    }

    private fun setupView() {
        setContentView(R.layout.activity_home)

        buttonCheckUserTweets.setOnClickListener {
            clickedOnContinue()
        }

        setupEditTextTwitterAccount()
    }

    private fun setupEditTextTwitterAccount() {
        editTextTwitterAccount.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        clickedOnContinue()
                        hideSoftInputWindow()
                        true
                    }
                    else -> false
                }
            }

            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.onViewEvent(ChangedUsername(editable.toString()))
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    Unit
            })
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { state ->
            when (state) {
                is HomeViewState.Idle -> displayIdleState(
                    state.isContinueButtonEnabled,
                    state.shouldDisplayEmptyUsernameError
                )
                HomeViewState.Loading -> showLoading()
                is HomeViewState.Error -> displayErrorState(state.error)
            }
        })
    }

    private fun observeSingleEvents() {
        viewModel.singleEvents.observe(this, Observer { event ->
            when (event) {
                HomeSingleEvent.NavigateToTweetsListScreen -> openTweetListScreen()
            }
        })
    }

    private fun displayIdleState(
        isContinueButtonDisabled: Boolean,
        shouldDisplayEmptyUsernameError: Boolean
    ) {
        hideLoading()

        buttonCheckUserTweets.isEnabled = isContinueButtonDisabled

        if (shouldDisplayEmptyUsernameError) {
            displayEmptyUsernameErrorMessage()
        } else {
            resetEmptyUsernameErrorMessage()
        }
    }

    private fun displayErrorState(error: Throwable) {
        hideLoading()

        when (error) {
            is UiError.EmptyField -> displayEmptyUsernameErrorMessage()
            is TwitterError.UserNotFound -> displayUserNotFoundMessage()
            else -> displayGenericErrorMessage()
        }
    }

    private fun displayEmptyUsernameErrorMessage() {
        textInputLayoutTwitterAccount.apply {
            error = getString(R.string.error_empty_twitter_account)
            isErrorEnabled = true
        }
    }

    private fun resetEmptyUsernameErrorMessage() {
        textInputLayoutTwitterAccount.apply {
            error = ""
            isErrorEnabled = false
        }
    }

    private fun displayUserNotFoundMessage() = displayErrorMessage(R.string.error_user_not_found)

    private fun displayGenericErrorMessage() = displayErrorMessage(R.string.error_generic)

    private fun resetErrorMessageState() {
        textViewErrorMessage.apply {
            text = ""
            visibility = View.GONE
        }
    }

    private fun displayErrorMessage(messageResourceId: Int) {
        textViewErrorMessage.apply {
            text = getString(messageResourceId)
            visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        resetErrorMessageState()
        loadingHolder.visibility = View.VISIBLE
        disableControls()
    }

    private fun hideLoading() {
        loadingHolder.visibility = View.GONE
        enableControls()
    }

    private fun clickedOnContinue() {
        val username = editTextTwitterAccount.text.toString()
        viewModel.onViewEvent(SubmitTwitterUsername(username))
    }

    override fun onBackPressed() = Unit

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
