package k7tech.agency.uiskills.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.R

class ItemsFragment : Fragment(), MyItemClickListener {
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
