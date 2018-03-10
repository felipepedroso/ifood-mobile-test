package br.pedroso.tweetsentiment.network.naturalLanguageApi.retrofit.entities.response

/**
 * Created by felip on 10/03/2018.
 */
data class Sentiment(val magnitude: Float, val score: Float)

data class SentenceText(val content: String, val beginOffset: Int)

data class Sentence(val text: SentenceText, val sentiment: Sentiment)

data class NaturalLanguageApiResponseBody(
        val documentSentiment: Sentiment,
        val language: String,
        val sentences: List<Sentence>)