package k7tech.agency.uiskills.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.R

class ItemsAdapter(private val itemClickListener: MyItemClickListener) :
    RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    private val itemsList = listOf(
        Item("Item 1", "Description 1"),
        Item("Item 2", "Description 2"),
        Item("Item 3", "Description 3"),
        Item("Item 4", "Description 4"),
        Item("Item 5", "Description 5"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val viewHolder = ItemsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
        viewHolder.itemView.setOnClickListener {
            itemClickListener.onItemClick(it.tag as Item)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemView.tag = item
        holder.bind(item)
    }

    override fun getItemCount(): Int = 5

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var switch = itemView.findViewById<SwitchCompat>(R.id.switch_item_clicked)
        private var title = itemView.findViewById<TextView>(R.id.item_title)
        private var description = itemView.findViewById<TextView>(R.id.item_description)

        fun bind(item: Item) {
            title.text = item.title
            description.text = item.description
            switch.isChecked = !switch.isChecked
        }
    }
}

interface MyItemClickListener {
    fun onItemClick(item: Item)
}