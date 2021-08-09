package k7tech.agency.uiskills.items

import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.Item
import k7tech.agency.uiskills.databinding.SimpleListItemBinding

class FeedItemsViewHolder(private val binding: SimpleListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) {
        binding.itemTitle.text = item.title
        binding.itemDescription.text = item.description
    }
}