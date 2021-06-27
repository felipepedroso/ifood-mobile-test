package br.pedroso.tweetsentiment.features.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.pedroso.tweetsentiment.coreandroid.extensions.disableControls
import br.pedroso.tweetsentiment.coreandroid.extensions.enableControls
import br.pedroso.tweetsentiment.coreandroid.extensions.hideSoftInput
import br.pedroso.tweetsentiment.domain.errors.UiError
import br.pedroso.tweetsentiment.domain.navigation.Navigator
import br.pedroso.tweetsentiment.domain.network.errors.TwitterError
import br.pedroso.tweetsentiment.features.home.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModel()

    private val navigator: Navigator by inject { parametersOf(this) }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setupView()
        observeViewState()
        observeSingleEvents()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupView() {
        binding.buttonCheckUserTweets.setOnClickListener {
            clickedOnContinue()
        }

        setupEditTextTwitterAccount()
    }

    private fun setupEditTextTwitterAccount() {
        binding.editTextTwitterAccount.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        clickedOnContinue()
                        requireActivity().hideSoftInput()
                        true
                    }
                    else -> false
                }
            }

            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    viewModel.onViewEvent(HomeViewEvent.ChangedUsername(editable.toString()))
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
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
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
        viewModel.singleEvents.observe(viewLifecycleOwner, Observer { event ->
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

        binding.buttonCheckUserTweets.isEnabled = isContinueButtonDisabled

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
        binding.textInputLayoutTwitterAccount.apply {
            error = getString(R.string.error_empty_twitter_account)
            isErrorEnabled = true
        }
    }

    private fun resetEmptyUsernameErrorMessage() {
        binding.textInputLayoutTwitterAccount.apply {
            error = ""
            isErrorEnabled = false
        }
    }

    private fun displayUserNotFoundMessage() = displayErrorMessage(R.string.error_user_not_found)

    private fun displayGenericErrorMessage() = displayErrorMessage(R.string.error_generic)

    private fun resetErrorMessageState() {
        binding.textViewErrorMessage.apply {
            text = ""
            visibility = View.GONE
        }
    }

    private fun displayErrorMessage(messageResourceId: Int) {
        binding.textViewErrorMessage.apply {
            text = getString(messageResourceId)
            visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        resetErrorMessageState()
        binding.loadingHolder.root.visibility = View.VISIBLE
        requireActivity().disableControls()
    }

    private fun hideLoading() {
        binding.loadingHolder.root.visibility = View.GONE
        requireActivity().enableControls()
    }

    private fun clickedOnContinue() {
        val username = binding.editTextTwitterAccount.text.toString()
        viewModel.onViewEvent(HomeViewEvent.SubmitTwitterUsername(username))
    }

    private fun openTweetListScreen() {
        navigator.openTweetsList()
    }
}
