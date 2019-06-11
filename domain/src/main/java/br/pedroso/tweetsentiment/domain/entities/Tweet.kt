package br.pedroso.tweetsentiment.domain.entities

import java.util.Date

data class Tweet(
    val id: Long,
    val text: String,
    val creationTimestamp: Date,
    val sentiment: Sentiment = Sentiment.NotAnalyzed
)
