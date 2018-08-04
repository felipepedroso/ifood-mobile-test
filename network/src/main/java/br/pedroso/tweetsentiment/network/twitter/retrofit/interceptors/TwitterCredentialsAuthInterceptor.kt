package br.pedroso.tweetsentiment.network.twitter.retrofit.interceptors

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class TwitterCredentialsAuthInterceptor(
        private val twitterConsumerKey: String,
        private val twitterConsumerSecret: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val authorization = Credentials.basic(twitterConsumerKey, twitterConsumerSecret)

        val modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", authorization)
                .build()

        return chain.proceed(modifiedRequest)
    }
}