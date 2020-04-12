package br.pedroso.tweetsentiment.domain.entities

import org.joda.time.DateTime

data class Tweet(
    val id: Long,
    val text: String,
    val creationTimestamp: DateTime,
    val sentiment: Sentiment = Sentiment.NotAnalyzed
)
