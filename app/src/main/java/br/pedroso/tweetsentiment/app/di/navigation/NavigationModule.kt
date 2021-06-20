package br.pedroso.tweetsentiment.app.di.navigation

import androidx.fragment.app.Fragment
import br.pedroso.tweetsentiment.app.navigation.AppNavigator
import br.pedroso.tweetsentiment.domain.navigation.Navigator
import org.koin.dsl.module

val navigationModule = module {
    factory<Navigator> { (fragment: Fragment) ->
        AppNavigator(fragment.requireContext())
    }
}
