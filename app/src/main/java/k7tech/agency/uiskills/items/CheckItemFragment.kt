package k7tech.agency.uiskills.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import k7tech.agency.uiskills.databinding.FragmentCheckItemBinding

class CheckItemFragment : BottomSheetDialogFragment() {

    private val args: CheckItemFragmentArgs by navArgs()
    private var _binding: FragmentCheckItemBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCheckItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textViewBottomSlideItemTitle.text = args.title

        binding.buttonCheckItem.setOnClickListener {
            parentFragmentManager.setFragmentResult(ITEM_TITLE, bundleOf(TITLE to args.title))
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

const val ITEM_TITLE = "Item title"
const val TITLE = "title"
