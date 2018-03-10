package br.pedroso.tweetsentiment.device.storage.preferences.hawk

import android.content.Context
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import com.orhanobut.hawk.Hawk

/**
 * Created by felip on 09/03/2018.
 */
class HawkApplicationPreferences(context: Context) : ApplicationPreferences {
    init {
        Hawk.init(context).build()
    }

    override fun storeUsernameToSync(username: String) {
        Hawk.put(CURRENT_USERNAME, username)
    }

    override fun retrieveUsernameToSync(): String {
        return Hawk.get<String>(CURRENT_USERNAME, "")
    }

    override fun cleanUsernameToSync() {
        if (Hawk.contains(CURRENT_USERNAME)) {
            Hawk.delete(CURRENT_USERNAME)
        }
    }

    override fun retrieveRecurrentSyncTimeInterval(): Int {
        return Hawk.get(RECURRENT_SYNC_TIME_INTERVAL, 60)
    }

    companion object {
        const val CURRENT_USERNAME = "current_user"
        const val RECURRENT_SYNC_TIME_INTERVAL = "recurrent_syn_time_interval"
    }
}