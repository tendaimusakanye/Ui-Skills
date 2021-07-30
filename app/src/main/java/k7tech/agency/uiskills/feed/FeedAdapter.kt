package k7tech.agency.uiskills.feed

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import k7tech.agency.uiskills.history.HistoryFragment
import k7tech.agency.uiskills.items.ItemsFragment

class FeedAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ItemsFragment()
            else -> HistoryFragment()
        }
    }

}