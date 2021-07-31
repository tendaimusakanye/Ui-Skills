package k7tech.agency.uiskills.items

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.Item
import k7tech.agency.uiskills.R

class FeedItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var title = itemView.findViewById<TextView>(R.id.item_title)
    private var description = itemView.findViewById<TextView>(R.id.item_description)

    fun bind(item: Item) {
        title.text = item.title
        description.text = item.description
    }
}