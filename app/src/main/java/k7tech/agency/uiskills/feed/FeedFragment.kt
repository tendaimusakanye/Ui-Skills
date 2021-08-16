package k7tech.agency.uiskills.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import k7tech.agency.uiskills.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private lateinit var feedAdapter: FeedAdapter
    private var _binding: FragmentFeedBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        feedAdapter = FeedAdapter(this)

        with(binding) {
            feedViewPager.adapter = feedAdapter

            TabLayoutMediator(feedTabLayout, feedViewPager) { tab, position ->
                val tabTitles = arrayOf("Items", "History")
                tab.text = tabTitles[position]
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

