package br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors

import br.pedroso.tweetsentiment.domain.device.storage.ApplicationSettings
import okhttp3.Interceptor
import okhttp3.Response

class TwitterRequestBearerInterceptor(
    private val applicationSettings: ApplicationSettings
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (applicationSettings.hasTwitterAccessToken()) {
            val twitterAccessToken = applicationSettings.retrieveTwitterAccessToken()

            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $twitterAccessToken")
                .build()

            return chain.proceed(modifiedRequest)
        }

        return chain.proceed(originalRequest)
    }
}
