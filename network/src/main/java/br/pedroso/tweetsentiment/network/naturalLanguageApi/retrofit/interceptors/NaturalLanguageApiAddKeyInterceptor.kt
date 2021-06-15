package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class NaturalLanguageApiAddKeyInterceptor(private val naturalLanguageApiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter(KEY_QUERY_PARAMETER, naturalLanguageApiKey)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(modifiedRequest)
    }

    companion object {
        const val KEY_QUERY_PARAMETER = "key"
    }
}
