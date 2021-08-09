package k7tech.agency.uiskills.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import k7tech.agency.uiskills.Item
import k7tech.agency.uiskills.databinding.SimpleListItemBinding
import k7tech.agency.uiskills.items.FeedItemsViewHolder
import k7tech.agency.uiskills.items.MyItemClickListener

class HistoryAdapter(private val itemClickListener: MyItemClickListener) :
    ListAdapter<Item, FeedItemsViewHolder>(HistoryItemsDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemsViewHolder {
        val viewHolder =
            FeedItemsViewHolder(SimpleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.itemView.setOnClickListener { itemClickListener.onItemClick(it.tag as Item) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FeedItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.tag = item
        holder.bind(item)
    }

    fun getPositionOfItem(title: String): Int {
        val item = currentList.find { it.title == title }
        return currentList.indexOf(item)
    }
}

class HistoryItemsDiff : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id && oldItem.title == newItem.title
    }
}