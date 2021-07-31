package k7tech.agency.uiskills.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import k7tech.agency.uiskills.R
import k7tech.agency.uiskills.items.ITEM_TITLE
import k7tech.agency.uiskills.items.TITLE

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentFragmentManager.setFragmentResultListener(ITEM_TITLE, this) { _, bundle ->
            val title = bundle.getString(TITLE)
            if (title != null) viewModel.checkItemWithTitle(title)
        }
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.feed_view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.feed_tab_layout)

        feedAdapter = FeedAdapter(this)
        viewPager.adapter = feedAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabTitles = arrayOf("Items", "History")
            tab.text = tabTitles[position]
        }.attach()
    }
}

