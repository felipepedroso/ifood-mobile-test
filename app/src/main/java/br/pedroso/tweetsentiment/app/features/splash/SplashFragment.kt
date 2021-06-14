package br.pedroso.tweetsentiment.app.features.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.pedroso.tweetsentiment.R
import br.pedroso.tweetsentiment.app.features.home.HomeActivity
import br.pedroso.tweetsentiment.app.features.tweetsList.TweetsListActivity
import br.pedroso.tweetsentiment.presentation.features.splash.SplashSingleEvent
import br.pedroso.tweetsentiment.presentation.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSingleEvents()
    }

    private fun observeSingleEvents() {
        splashViewModel.singleEvents.observe(viewLifecycleOwner, Observer { event ->
            when (event) {
                SplashSingleEvent.NavigateToHomeScreen -> openHomeScreen()
                SplashSingleEvent.NavigateToTweetsListScreen -> openTweetListScreen()
            }
        })
    }

    private fun openHomeScreen() {
        HomeActivity.navigateHere(requireContext())
        requireActivity().finish()
    }

    private fun openTweetListScreen() {
        TweetsListActivity.navigateHereWithHomeAtBackstack(requireContext())
        requireActivity().finish()
    }
}
