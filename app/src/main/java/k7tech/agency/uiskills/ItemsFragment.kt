package k7tech.agency.uiskills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class ItemsFragment : Fragment() {
    private val itemsAdapter = ItemsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.items_recycler_view)
        recyclerView.adapter = itemsAdapter
        recyclerView.setHasFixedSize(true)
    }
}

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    private val itemsList = listOf(
        Item("Item 1", "Description 1"),
        Item("Item 2", "Description 2"),
        Item("Item 3", "Description 3"),
        Item("Item 4", "Description 4"),
        Item("Item 5", "Description 5"),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = 5

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.item_title)
        private var description = itemView.findViewById<TextView>(R.id.item_description)

        fun bind(item: Item) {
            title.text = item.title
            description.text = item.description
        }
    }
}

data class Item(
    var title: String,
    var description: String
)

