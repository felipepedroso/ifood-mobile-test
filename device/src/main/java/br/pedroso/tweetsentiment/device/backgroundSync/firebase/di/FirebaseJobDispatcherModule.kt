package br.pedroso.tweetsentiment.device.backgroundSync.firebase.di

import android.content.Context
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton

/**
 * Created by felip on 11/03/2018.
 */
class FirebaseJobDispatcherModule(private val context: Context) {
    val graph = Kodein.Module {
        bind<FirebaseJobDispatcher>() with singleton {
            FirebaseJobDispatcher(GooglePlayDriver(context))
        }
    }
}