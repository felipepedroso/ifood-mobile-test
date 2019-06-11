package br.pedroso.tweetsentiment.domain.network.errors

sealed class TwitterError : Throwable() {
    class UserNotFound : TwitterError()
    class EmptyResponse : TwitterError()
    class UndesiredResponse : TwitterError()
    class AuthenticationError : TwitterError()
}
