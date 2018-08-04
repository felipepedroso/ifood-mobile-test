package br.pedroso.tweetsentiment.domain.errors

sealed class UiError : Throwable() {
    class EmptyField(val fieldName: String = "") : UiError()
}