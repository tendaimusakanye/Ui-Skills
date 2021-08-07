package k7tech.agency.uiskills.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import k7tech.agency.uiskills.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        return FragmentSearchBinding.inflate(inflater, container, false).root
    }
}