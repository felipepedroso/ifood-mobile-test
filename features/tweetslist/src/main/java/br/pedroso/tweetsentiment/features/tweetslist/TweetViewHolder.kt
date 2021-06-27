package br.pedroso.tweetsentiment.features.tweetslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.tweetsentiment.domain.entities.Sentiment
import br.pedroso.tweetsentiment.domain.entities.Tweet
import br.pedroso.tweetsentiment.features.tweetslist.databinding.ItemTweetBinding
import br.pedroso.tweetsentiment.features.tweetslist.utils.resourceColor
import br.pedroso.tweetsentiment.features.tweetslist.utils.resourceIcon
import net.danlew.android.joda.DateUtils


class TweetViewHolder(
    private val binding: ItemTweetBinding,
    private val buttonAnalyzeSentimentClickListener: (Tweet) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tweet: Tweet) {
        with(tweet) {
            binding.content.textViewTweetContent.text = text
            binding.content.textViewTweetTimestamp.text = DateUtils.getRelativeTimeSpanString(
                binding.root.context,
                creationTimestamp,
                DateUtils.FORMAT_ABBREV_RELATIVE
            )

            binding.content.tweetSentimentChip.apply {
                text = sentiment.name
                setChipBackgroundColorResource(sentiment.resourceColor)
                setChipIconResource(sentiment.resourceIcon)
                visibility = View.INVISIBLE
            }

            if (sentiment == Sentiment.NotAnalyzed) {
                binding.content.buttonAnalyzeSentiment.setOnClickListener {
                    buttonAnalyzeSentimentClickListener(tweet)
                }
                binding.content.buttonAnalyzeSentiment.visibility = View.VISIBLE
            } else {
                binding.content.buttonAnalyzeSentiment.visibility = View.INVISIBLE
                binding.content.tweetSentimentChip.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            buttonAnalyzeSentimentClickListener: (Tweet) -> Unit
        ): TweetViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTweetBinding.inflate(layoutInflater, parent, false)
            return TweetViewHolder(binding, buttonAnalyzeSentimentClickListener)
        }
    }
}