package br.pedroso.tweetsentiment.app.features.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.base.BaseActivity
import br.pedroso.tweetsentiment.app.features.home.HomeActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.presentation.features.splash.SplashSingleEvent.NavigateToHomeScreen
import br.pedroso.tweetsentiment.presentation.features.splash.SplashSingleEvent.NavigateToTweetsListScreen
import br.pedroso.tweetsentiment.presentation.features.splash.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        observeSingleEvents()
    }

    private fun observeSingleEvents() {
        splashViewModel.singleEvents.observe(this, Observer { event ->
            when (event) {
                NavigateToHomeScreen -> openHomeScreen()
                NavigateToTweetsListScreen -> openTweetListScreen()
            }
        })
    }

    private fun openHomeScreen() {
        HomeActivity.navigateHere(this)
        finish()
    }

    private fun openTweetListScreen() {
        TweetsListActivity.navigateHereWithHomeAtBackstack(this)
        finish()
    }
}
