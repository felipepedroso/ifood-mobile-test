package br.pedroso.tweetsentiment.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.FragmentActivity

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> androidx.fragment.app.FragmentActivity.viewModelProvider(
        mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
        crossinline provider: () -> VM) = lazy(mode) {

    val factory = object : ViewModelProvider.Factory {
        override fun <Model : ViewModel> create(klass: Class<Model>) = provider() as Model
    }

    ViewModelProviders.of(this, factory).get(VM::class.java)
}