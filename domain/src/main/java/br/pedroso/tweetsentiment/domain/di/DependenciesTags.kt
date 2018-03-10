package br.pedroso.tweetsentiment.domain.di

/**
 * Created by felip on 08/03/2018.
 */
class DependenciesTags {
    companion object {
        const val WORKER_SCHEDULER = "worker"
        const val UI_SCHEDULER = "main"
        const val DATABASE = "db"
        const val NETWORK_CACHE_DIR = "cache-dir"
        const val NATURAL_LANGUAGE_OKHTTP_CLIENT = "okHttpClient-nla"
        const val NATURAL_LANGUAGE_RETROFIT = "retrofit-nla"
        const val TWITTER_RETROFIT: String = "retrofit-twitter"
        const val TWITTER_OKHTTP_CLIENT: String = "okHttpClient-twitter"
        const val TWITTER_AUTH_RETROFIT: String = "retrofit-twitter"
        const val TWITTER_AUTH_OKHTTP_CLIENT: String = "okHttpClient-twitter-auth"
    }
}