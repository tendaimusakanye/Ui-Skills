package k7tech.agency.uiskills.feed

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import k7tech.agency.uiskills.history.FeedHistoryFragment
import k7tech.agency.uiskills.items.FeedItemsFragment

class FeedAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FeedItemsFragment()
            else -> FeedHistoryFragment()
        }
    }
}