package uz.gita.recentnews.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.recentnews.R
import uz.gita.recentnews.data.common.SnapData


class SnapCategoryAdapter : RecyclerView.Adapter<SnapCategoryAdapter.SnapCategoryViewHolder>() {

    private var listener: ((String, String) -> Unit)? = null
    fun setLyambda(category: (String, String) -> Unit) {
        this.listener = category
    }

    var categoryList = listOf<SnapData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class SnapCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView? = null
        var image: ImageView? = null
        var continer: LinearLayout? = null

        init {
            name = view.findViewById(R.id.name)
            image = view.findViewById(R.id.image)
            continer = view.findViewById(R.id.lyt_parent)
        }


        fun bind(data: SnapData) {
            name!!.text = data.title
            image!!.setImageResource(data.image)
            continer!!.setOnClickListener {
                listener?.invoke(data.query, data.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnapCategoryViewHolder {
        return SnapCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_snap_full, parent, false))

    }

    override fun onBindViewHolder(holder: SnapCategoryViewHolder, position: Int) = holder.bind(categoryList[position])
    override fun getItemCount(): Int = categoryList.size

}
