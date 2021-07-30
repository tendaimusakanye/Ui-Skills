package k7tech.agency.uiskills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class ItemsFragment : Fragment(),MyItemClickListener {
    private val itemsAdapter = ItemsAdapter(this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.items_recycler_view)
        recyclerView.adapter = itemsAdapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onItemClick(item: Item) {
        findNavController().navigate(R.id.open_modal_sheet)
    }
}

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
        viewHolder.itemView.setOnClickListener { view ->
            itemClickListener.onItemClick(view.tag as Item)
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

interface MyItemClickListener {
    fun onItemClick(item: Item)
}

