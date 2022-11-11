package uz.gita.recentnews_slp.ui.adapter

import android.util.Log
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
import uz.gita.recentnews_slp.R
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity
import java.text.SimpleDateFormat
import java.util.*

class MainListAdapter (val hashtag: String) : ListAdapter<TopNewsEntity, MainListAdapter.NewsListViewHolder>(NewsDiffUtil) {

    private var listener: ((TopNewsEntity) -> Unit)? = null

    object NewsDiffUtil : DiffUtil.ItemCallback<TopNewsEntity>() {

        override fun areItemsTheSame(oldItem: TopNewsEntity, newItem: TopNewsEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TopNewsEntity, newItem: TopNewsEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class NewsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var buttonNext: ConstraintLayout? = null
        var isfav: AppCompatCheckBox? = null
        var title: TextView? = null
        var author: TextView? = null
        var _hashTag: TextView? = null
        var timestamp: TextView? = null
        var image: ImageView? = null
        var descriptionNews: TextView? = null

        init {
            buttonNext = view.findViewById(R.id.clickItem)
            title = view.findViewById(R.id.titleNews)
            author = view.findViewById(R.id.authorNews)
            timestamp = view.findViewById(R.id.time_stamp)
            image = view.findViewById(R.id.imageNews)
            _hashTag = view.findViewById(R.id.hashtag)
            descriptionNews = view.findViewById(R.id.descriptionNews)
            isfav = view.findViewById(R.id.isFav)
        }

        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            title!!.text = item.title
            author!!.text = item.author
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val netDate = Date(item.timestamp.toLong())
                timestamp!!.text = sdf.format(netDate)
            } catch (e: Exception) {
                Log.d("TAG", "bind: "+ e.toString())
            }

            _hashTag!!.text = hashtag
            Glide.with(itemView)
                .load(item.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
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
    fun setListener(block: (TopNewsEntity) -> Unit) {
        listener = block
    }
}
