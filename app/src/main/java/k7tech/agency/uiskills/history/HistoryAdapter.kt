package k7tech.agency.uiskills.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.R
import k7tech.agency.uiskills.items.Item

class HistoryAdapter : ListAdapter<Item, HistoryAdapter.HistoryViewHolder>(HistoryItemsDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.item_title)
        private var description = itemView.findViewById<TextView>(R.id.item_description)

        fun bind(item: Item) {
            title.text = item.title
            description.text = item.description
        }
    }
}

class HistoryItemsDiff : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.description == newItem.description
    }
}