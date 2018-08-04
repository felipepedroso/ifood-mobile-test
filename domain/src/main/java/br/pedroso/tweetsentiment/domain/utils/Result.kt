package br.pedroso.tweetsentiment.domain.utils

sealed class Result {
    class Empty : Result()
    class WithValue<T>(val value: T) : Result()
}