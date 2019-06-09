package br.pedroso.tweetsentiment.domain.utils

sealed class Result {
    object Empty : Result()
    class WithValue<T>(val value: T) : Result()
}