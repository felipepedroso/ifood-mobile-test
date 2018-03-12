package br.pedroso.tweetsentiment.app.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.presentation.features.home.HomePresenter
import br.pedroso.tweetsentiment.presentation.features.home.HomeView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_loading_feedback.*

class HomeActivity : BaseActivity(), HomeView {
    private val kodein by lazy { LazyKodein(appKodein) }
    private val presenter by kodein.with(this).instance<HomePresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.releaseSubscriptions()
    }

    override fun onResume() {
        super.onResume()
        presenter.viewResumed()
    }

    private fun setupView() {
        setContentView(R.layout.activity_home)

        buttonCheckUserTweets.setOnClickListener {
            presenter.clickedCheckUserTweets()
        }

        editTextTwitterAccount.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    presenter.clickedCheckUserTweets()
                    true
                }
                else -> false
            }
        }
    }

    override fun getUsernameFromInput(): String {
        return editTextTwitterAccount.text.toString()
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

    private fun disableControls() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun enableControls() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
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

    override fun openTweetListScreen() {
        TweetsListActivity.navigateHere(this)
    }

    companion object {
        fun navigateHere(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}
