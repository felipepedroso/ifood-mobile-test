package br.pedroso.tweetsentiment.domain.utils

class Optional<T> private constructor(val value: T?) {

    private constructor() : this(null)

    companion object {
        fun <T> of(value: T) = Optional(value)

        fun <T> ofNullable(value: T?) = Optional(value)

        fun <T> empty() = Optional<T>()
    }
}
