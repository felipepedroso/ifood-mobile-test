package br.pedroso.tweetsentiment.domain.entities

sealed class ViewState {
    class Loading : ViewState()
    class ShowContent<out T>(val contentValue: T) : ViewState()
    class Success : ViewState()
    class Done : ViewState()
    class Error(val error: Throwable) : ViewState()
    class Empty : ViewState()

    override fun toString() = this.javaClass.simpleName!!
}
