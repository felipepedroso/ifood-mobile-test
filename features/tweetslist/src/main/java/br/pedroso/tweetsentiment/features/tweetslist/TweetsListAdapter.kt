package br.pedroso.tweetsentiment.features.tweetslist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.pedroso.tweetsentiment.domain.entities.Tweet

class TweetsListAdapter(
    private val buttonAnalyzeSentimentClickListener: (Tweet) -> Unit
) : ListAdapter<Tweet, TweetViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        return TweetViewHolder.create(parent, buttonAnalyzeSentimentClickListener)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tweet>() {
            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean =
                oldItem == newItem
        }
    }
}
