package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request

/**
 * Created by felip on 10/03/2018.
 */
data class Document(val content: String, val type: String = "PLAIN_TEXT")

data class NaturalLanguageApiRequestBody(val document: Document, val encodingType: String = "UTF8")