package br.pedroso.tweetsentiment.features.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.pedroso.tweetsentiment.domain.navigation.Navigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val splashViewModel: SplashViewModel by viewModel()

    private val navigator: Navigator by inject { parametersOf(this) }

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
        navigator.openHome()
        requireActivity().finish()
    }

    private fun openTweetListScreen() {
        navigator.openTweetsListWithHomeOnBackstack()
        requireActivity().finish()
    }
}
