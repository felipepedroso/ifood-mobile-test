package br.pedroso.tweetsentiment.app.di

object DependenciesTags {
    const val WORKER_DISPATCHER = "worker"
    const val NETWORK_CACHE_DIR = "cache-dir"
    const val NATURAL_LANGUAGE_OKHTTP_CLIENT = "okHttpClient-nla"
    const val NATURAL_LANGUAGE_RETROFIT = "retrofit-nla"
    const val TWITTER_RETROFIT: String = "retrofit-twitter"
    const val TWITTER_OKHTTP_CLIENT: String = "okHttpClient-twitter"
    const val TWITTER_AUTH_RETROFIT: String = "retrofit-twitter-auth"
    const val TWITTER_AUTH_OKHTTP_CLIENT: String = "okHttpClient-twitter-auth"
}
