package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.request

data class Document(val content: String, val type: String = "PLAIN_TEXT")

data class NaturalLanguageApiRequestBody(val document: Document, val encodingType: String = "UTF8")
