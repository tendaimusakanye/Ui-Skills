package k7tech.agency.uiskills.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import k7tech.agency.uiskills.R

class HistoryFragment : Fragment() {
    private val historyAdapter = HistoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.items_recycler_view)
        recyclerView.adapter = historyAdapter
        recyclerView.setHasFixedSize(true)
    }
}

