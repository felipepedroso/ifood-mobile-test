package br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationPreferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by felip on 10/03/2018.
 */
class TwitterRequestBearerInterceptor(
        private val applicationPreferences: ApplicationPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (applicationPreferences.hasTwitterAccessToken()) {
            val twitterAccessToken = applicationPreferences.retrieveTwitterAccessToken()

            val modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $twitterAccessToken")
                    .build()

            return chain.proceed(modifiedRequest)
        }

        return chain.proceed(originalRequest)
    }
}