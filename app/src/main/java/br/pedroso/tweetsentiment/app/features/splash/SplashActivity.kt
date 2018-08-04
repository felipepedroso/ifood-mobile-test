package br.pedroso.tweetsentiment.app.features.splash

import android.os.Bundle
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.app.features.home.HomeActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.app.utils.viewModelProvider
import br.pedroso.tweetsentiment.domain.entities.ViewState
import br.pedroso.tweetsentiment.presentation.features.splash.SplashViewModel
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import timber.log.Timber

class SplashActivity : BaseActivity() {
    private val kodein by lazy { LazyKodein(appKodein) }
    private val splashViewModel by viewModelProvider { kodein.value.instance<SplashViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        tryToSyncRegisteredUser()
    }

    private fun tryToSyncRegisteredUser() {
        val subscription =
                splashViewModel.syncRegisteredUser()
                        .subscribe(
                                { handleNewViewState(it) },
                                { Timber.e("Unknow exception: $it") }
                        )

        registerDisposable(subscription)
    }

    private fun handleNewViewState(viewState: ViewState) = when (viewState) {
        is ViewState.Empty -> openHomeScreen()
        is ViewState.ShowContent<*> -> openTweetListScreen()
        else -> Timber.d("State not used: $viewState")
    }

    private fun openHomeScreen() {
        HomeActivity.navigateHere(this)
    }

    private fun openTweetListScreen() {
        TweetsListActivity.navigateHere(this)
    }
}