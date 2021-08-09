package k7tech.agency.uiskills.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.Item
import k7tech.agency.uiskills.databinding.SimpleListItemBinding

class FeedItemsAdapter(private val itemClickListener: MyItemClickListener) :
    RecyclerView.Adapter<FeedItemsViewHolder>() {

    private val itemsList = listOf(
        Item(786, "Item 1", "Description 1"),
        Item(4, "Item 2", "Description 2"),
        Item(4567, "Item 3", "Description 3"),
        Item(90, "Item 4", "Description 4"),
        Item(34, "Item 5", "Description 5")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemsViewHolder {
        val viewHolder =
            FeedItemsViewHolder(SimpleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        viewHolder.itemView.setOnClickListener { itemClickListener.onItemClick(it.tag as Item) }
        return viewHolder
    }

    fun getPositionOfItem(title: String): Int {
        val item = itemsList.find { it.title == title }
        return itemsList.indexOf(item)
    }

    override fun onBindViewHolder(holder: FeedItemsViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemView.tag = item
        holder.bind(item)
    }

    override fun getItemCount(): Int = 5
}

