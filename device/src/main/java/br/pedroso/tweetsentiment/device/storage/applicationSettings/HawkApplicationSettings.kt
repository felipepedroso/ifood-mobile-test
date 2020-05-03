package br.pedroso.tweetsentiment.device.storage.applicationSettings

import android.content.Context
import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import com.orhanobut.hawk.Hawk

class HawkApplicationSettings(context: Context) : ApplicationSettings {
    init {
        Hawk.init(context).build()
    }

    override fun storeUsernameToSync(username: String) {
        Hawk.put(CURRENT_USERNAME, username)
    }

    override fun retrieveUsernameToSync(): String {
        return Hawk.get(CURRENT_USERNAME, "")
    }

    override fun cleanUsernameToSync() {
        if (Hawk.contains(CURRENT_USERNAME)) {
            Hawk.delete(CURRENT_USERNAME)
        }
    }

    override fun retrieveRecurrentSyncTimeInterval(): Int {
        return Hawk.get(RECURRENT_SYNC_TIME_INTERVAL, DEFAULT_RECURRENT_SYNC_TIME_INTERVAL)
    }

    override fun storeTwitterAccessToken(accessToken: String) {
        Hawk.put(TWITTER_ACCESS_TOKEN, accessToken)
    }

    override fun hasTwitterAccessToken(): Boolean {
        return Hawk.contains(TWITTER_ACCESS_TOKEN)
    }

    override fun retrieveTwitterAccessToken(): String {
        return Hawk.get(TWITTER_ACCESS_TOKEN)
    }

    companion object {
        const val CURRENT_USERNAME = "current_user"
        const val RECURRENT_SYNC_TIME_INTERVAL = "recurrent_syn_time_interval"
        const val TWITTER_ACCESS_TOKEN = "twitter_access_token"
        const val DEFAULT_RECURRENT_SYNC_TIME_INTERVAL = 60
    }
}
