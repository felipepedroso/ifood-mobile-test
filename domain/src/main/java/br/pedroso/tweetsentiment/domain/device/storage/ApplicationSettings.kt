package br.pedroso.tweetsentiment.domain.device.storage

/**
 * Created by felip on 08/03/2018.
 */
interface ApplicationSettings {
    fun storeUsernameToSync(username: String)
    fun retrieveUsernameToSync(): String
    fun cleanUsernameToSync()
    fun retrieveRecurrentSyncTimeInterval(): Int
    fun storeTwitterAccessToken(accessToken: String)
    fun hasTwitterAccessToken(): Boolean
    fun retrieveTwitterAccessToken(): String
}