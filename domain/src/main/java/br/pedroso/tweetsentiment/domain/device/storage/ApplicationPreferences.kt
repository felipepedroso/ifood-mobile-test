package br.pedroso.tweetsentiment.domain.device.storage

/**
 * Created by felip on 08/03/2018.
 */
interface ApplicationPreferences {
    fun storeCurrentUsername(username: String)
    fun retrieveCurrentUsername() : String
    fun cleanCurrentUsername()
    fun retrieveRecurrentSyncTimeInterval(): Int
}