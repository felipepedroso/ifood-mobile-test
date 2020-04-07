package br.pedroso.tweetsentiment.domain.network.dataSources

import br.pedroso.tweetsentiment.domain.entities.Sentiment

interface SentimentAnalysisDataSource {
    suspend fun analyzeSentimentFromText(text: String): Sentiment
}
