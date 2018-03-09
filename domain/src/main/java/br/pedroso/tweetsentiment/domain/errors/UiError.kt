package br.pedroso.tweetsentiment.domain.errors

/**
 * Created by felip on 08/03/2018.
 */
sealed class UiError : Throwable() {
    class EmptyField(val fieldName: String = "") : UiError()
}