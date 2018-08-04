package br.pedroso.tweetsentiment.domain

import java.util.*

data class Tweet(
        val id: Long,
        val text: String,
        val creationTimestamp: Date,
        val sentiment: Sentiment = Sentiment.NotAnalyzed)