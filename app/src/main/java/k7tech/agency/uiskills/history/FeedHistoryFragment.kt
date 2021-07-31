package k7tech.agency.uiskills.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.Item
import k7tech.agency.uiskills.R
import k7tech.agency.uiskills.feed.FeedFragmentDirections
import k7tech.agency.uiskills.feed.FeedViewModel
import k7tech.agency.uiskills.items.MyItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedHistoryFragment : Fragment(), MyItemClickListener {

    private val viewModel: FeedViewModel by viewModels({ requireParentFragment() })
    private val historyAdapter = HistoryAdapter(this)
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.items_recycler_view)
        recyclerView.adapter = historyAdapter
        recyclerView.setHasFixedSize(true)

        viewModel.item.observe(viewLifecycleOwner, { newList ->
            historyAdapter.submitList(newList)
        })

        viewModel.title.observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenResumed {
                /*
                Items are checked properly when the fragment's view has been created first. I.e. when the user
                has opened the fragment. If the fragment hasn't been opened yet the checking mechanism will give
                unexpected results.
                 */
                checkItem(it)
            }
        })
    }

    private fun checkItem(title: String) {
        lifecycleScope.launch(Dispatchers.Default) {
            val position = historyAdapter.getPositionOfItem(title)
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val checkBox = viewHolder?.itemView?.findViewById<CheckBox>(R.id.checkBox)

            launch(Dispatchers.Main) {
                checkBox?.isChecked = true
            }
        }
    }

    override fun onItemClick(item: Item) {
        viewModel.onItemClicked(item)
        findNavController().navigate(FeedFragmentDirections.toBottomSheet(item.title))
    }
}

