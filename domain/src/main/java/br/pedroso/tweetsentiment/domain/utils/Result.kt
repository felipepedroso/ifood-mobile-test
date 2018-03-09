package br.pedroso.tweetsentiment.domain.utils

/**
 * Created by felipe on 1/19/18.
 */
sealed class Result {
    class Empty : Result()
    class WithValue<T>(val value: T) : Result()
}