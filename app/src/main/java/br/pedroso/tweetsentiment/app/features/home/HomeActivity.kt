package br.pedroso.tweetsentiment.app.features.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.app.utils.viewModelProvider
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import br.pedroso.tweetsentiment.presentation.features.home.HomeViewModel
import br.pedroso.tweetsentiment.presentation.features.home.presenters.HomeSyncRegisteredUserPresenter
import br.pedroso.tweetsentiment.presentation.features.home.presenters.HomeSyncUserPresenter
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_loading_feedback.*
import timber.log.Timber

class HomeActivity : BaseActivity(), HomeView {
    private val kodein by lazy { LazyKodein(appKodein) }
    private val homeViewModel by viewModelProvider { kodein.value.instance<HomeViewModel>() }
    private val homeSyncUserPresenter by kodein.with(this).instance<HomeSyncUserPresenter>()
    private val homeSyncRegisteredUserPresenter by kodein.with(this).instance<HomeSyncRegisteredUserPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
    }

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
                resetErrorMessageState().run()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun clickedCheckUserTweets() {
        val username = editTextTwitterAccount.text.toString()

        val subscription =
                homeViewModel.syncNewUser(username)
                        .compose(homeSyncUserPresenter)
                        .subscribe(
                                { Timber.d("Loaded the new user!") },
                                { Timber.e(it) })

        registerDisposable(subscription)
    }

    override fun onResume() {
        super.onResume()
        tryToSyncRegisteredUser()
    }

    private fun tryToSyncRegisteredUser() {
        val subscription =
                homeViewModel.syncRegisteredUser()
                        .compose(homeSyncRegisteredUserPresenter)
                        .subscribe(
                                { Timber.d("Loaded the registered user!") },
                                { Timber.d("No user to sync!") }
                        )
        registerDisposable(subscription)
    }

    private fun enableContinueButton() {
        buttonCheckUserTweets.isEnabled = true
    }

    private fun disableContinueButton() {
        buttonCheckUserTweets.isEnabled = false
    }

    override fun showEmptyUsernameErrorMessage() = Action {
        textInputLayoutTwitterAccount.error = getString(R.string.error_empty_twitter_account)
        textInputLayoutTwitterAccount.isErrorEnabled = true
    }

    override fun hideEmptyUsernameErrorMessage() = Action {
        textInputLayoutTwitterAccount.error = ""
        textInputLayoutTwitterAccount.isErrorEnabled = false
    }

    override fun showErrorState(it: Throwable) = displayErrorMessage(R.string.error_generic)

    override fun showLoading() = Action {
        loadingHolder.visibility = View.VISIBLE
        disableControls()
    }

    override fun showUserNotFoundState() = displayErrorMessage(R.string.error_user_not_found)

    private fun displayErrorMessage(messageResourceId: Int) = Action {
        textViewErrorMessage.text = getString(messageResourceId)
        textViewErrorMessage.visibility = View.VISIBLE
    }

    override fun hideLoading() = Action {
        loadingHolder.visibility = View.GONE
        enableControls()
    }

    override fun hideErrorState() = resetErrorMessageState()

    override fun hideUserNotFoundState() = resetErrorMessageState()

    private fun resetErrorMessageState() = Action {
        textViewErrorMessage.text = ""
        textViewErrorMessage.visibility = View.GONE
    }

    override fun openTweetListScreen() = Action {
        TweetsListActivity.navigateHere(this)
    }
}
