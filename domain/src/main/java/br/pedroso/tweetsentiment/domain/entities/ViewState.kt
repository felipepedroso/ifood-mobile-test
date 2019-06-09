package br.pedroso.tweetsentiment.domain.entities

sealed class ViewState {
    object Loading : ViewState()
    class ShowContent<out T>(val contentValue: T) : ViewState()
    object Success : ViewState()
    object Done : ViewState()
    class Error(val error: Throwable) : ViewState()
    object Empty : ViewState()

    override fun toString() = this.javaClass.simpleName!!
}
