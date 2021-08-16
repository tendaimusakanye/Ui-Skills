package k7tech.agency.uiskills.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import k7tech.agency.uiskills.Item
import k7tech.agency.uiskills.MainActivityViewModel
import k7tech.agency.uiskills.R
import k7tech.agency.uiskills.databinding.FragmentFeedItemsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedItemsFragment : Fragment(), MyItemClickListener {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private val itemsAdapter = FeedItemsAdapter(this)
    private var _binding: FragmentFeedItemsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFeedItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            itemsRecyclerView.adapter = itemsAdapter
            itemsRecyclerView.setHasFixedSize(true)
        }

        viewModel.pair.observe(viewLifecycleOwner, {
            lifecycleScope.launchWhenResumed {
                if (it.first) checkItem(it.second)
            }
        })
    }

    private fun checkItem(title: String) {
        lifecycleScope.launch {
            val position = itemsAdapter.getPositionOfItem(title)
            val viewHolder = binding.itemsRecyclerView.findViewHolderForAdapterPosition(position)
            val checkBox = viewHolder?.itemView?.findViewById<CheckBox>(R.id.checkBox)

            launch(Dispatchers.Main) {
                checkBox?.isChecked = true
            }
        }
    }

    override fun onItemClick(item: Item) {
        viewModel.onItemClicked(item)
        viewModel.displayBottomSheet(item.title)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
