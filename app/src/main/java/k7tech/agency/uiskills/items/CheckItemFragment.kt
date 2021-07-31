package k7tech.agency.uiskills.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import k7tech.agency.uiskills.R

class CheckItemFragment : BottomSheetDialogFragment() {

    private val args: CheckItemFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.button_check_item)
        val itemTextView = view.findViewById<TextView>(R.id.text_view_bottom_slide_item_title)

        itemTextView.text = args.title

        button.setOnClickListener {
            parentFragmentManager.setFragmentResult(ITEM_TITLE, bundleOf(TITLE to args.title))
            dismiss()
        }
    }
}

const val ITEM_TITLE = "Item title"
const val TITLE = "title"
