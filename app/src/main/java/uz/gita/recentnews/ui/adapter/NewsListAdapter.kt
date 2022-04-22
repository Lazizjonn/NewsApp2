package uz.gita.recentnews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.recentnews.R
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity

class NewsListAdapter : ListAdapter<NewsEntity, NewsListAdapter.NewsListViewHolder>(NewsDiffUtil) {

    private var listener: ((NewsEntity) -> Unit)? = null

    object NewsDiffUtil : DiffUtil.ItemCallback<NewsEntity>() {

        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class NewsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var buttonNext: ConstraintLayout? = null
        var isfav: AppCompatCheckBox? = null
        var title: TextView? = null
        var author: TextView? = null
        var timestamp: TextView? = null
        var image: ImageView? = null
        var descriptionNews: TextView? = null

        init {
            buttonNext = view.findViewById(R.id.clickItem)
            title = view.findViewById(R.id.titleNews)
            author = view.findViewById(R.id.authorNews)
            timestamp = view.findViewById(R.id.time_stamp)
            image = view.findViewById(R.id.imageNews)
            descriptionNews = view.findViewById(R.id.descriptionNews)
            isfav = view.findViewById(R.id.isFav)
        }

        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            title!!.text = item.title
            author!!.text = item.author
            timestamp!!.text = item.timestamp
            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.error_icon)
                .error(R.drawable.error_icon)
                .into(image!!)
            descriptionNews!!.text = item.description
            isfav!!.isChecked = false

            buttonNext?.let {
                it.setOnClickListener {
                    listener?.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind()
    }

    fun setListener(block: (NewsEntity) -> Unit) {
        listener = block
    }
}
