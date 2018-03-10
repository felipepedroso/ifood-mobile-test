package br.pedroso.tweetsentiment.domain.network.errors

/**
 * Created by felip on 08/03/2018.
 */
sealed class TwitterError : Throwable() {
    class UserNotFound : TwitterError()
    class EmptyResponse : TwitterError()
    class UndesiredResponse : TwitterError()
    class AuthenticationError : TwitterError()
}