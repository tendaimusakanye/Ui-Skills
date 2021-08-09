package k7tech.agency.uiskills.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import k7tech.agency.uiskills.databinding.SimpleListItemBinding
import kotlin.math.max

class SimpleListItem(context: Context, attrs: AttributeSet) :
    ViewGroup(context, attrs) {

    private lateinit var binding: SimpleListItemBinding

    /**
     * Validates if a set of layout parameters is valid for a child of this ViewGroup.
     */
    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is MarginLayoutParams
    }

    /**
     * @return A set of default layout parameters when given a child with no layout parameters.
     */
    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    /**
     * @return A set of layout parameters created from attributes passed in XML.
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = SimpleListItemBinding.bind(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        with(binding) {
            //measure checkBox
            measureChildWithMargins(checkBox, widthMeasureSpec, 0, heightMeasureSpec, 0)
            //figure out how width the checkbox has used
            var layoutParams = checkBox.layoutParams as MarginLayoutParams
            val widthUsed = checkBox.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            val heightUsed = 0

            //Measure the itemTitle
            measureChildWithMargins(
                itemTitle,
                // Pass width constraints and width already used.
                widthMeasureSpec, widthUsed,
                // Pass height constraints and height already used.
                heightMeasureSpec, heightUsed
            )

            // Measure the itemDescription.
            measureChildWithMargins(
                itemDescription,
                // Pass width constraints and width already used.
                widthMeasureSpec, widthUsed,
                // Pass height constraints and height already used.
                heightMeasureSpec, itemTitle.measuredHeight
            )

            // Figure out how much total space the checkBox used.
            val checkBoxWidth = checkBox.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            val checkBoxHeight = checkBox.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            layoutParams = itemTitle.layoutParams as MarginLayoutParams

            // Figure out how much total space the itemTitle used.
            val titleWidth = itemTitle.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            val titleHeight = itemTitle.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            layoutParams = itemDescription.layoutParams as MarginLayoutParams

            // Figure out how much total space the itemDescription used.
            val descriptionWidth = itemDescription.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            val descriptionHeight = itemDescription.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin

            // The width taken by the children + padding.
            val width = paddingLeft + paddingRight + checkBoxWidth + max(titleWidth, descriptionWidth)
            // The height taken by the children + padding.
            val height = paddingTop + paddingBottom + max(checkBoxHeight, titleHeight + descriptionHeight)

            // Reconcile the measured dimensions with the this view's constraints and
            // set the final measured width and height.
            setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec))
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        with(binding) {
            var layoutParams = checkBox.layoutParams as MarginLayoutParams

            // figure out the checkBox's x and y co-ordinates
            var x = paddingLeft + layoutParams.leftMargin
            var y = paddingTop + layoutParams.topMargin

            // layout the checkbox
            checkBox.layout(x, y, x + checkBox.measuredWidth, y + checkBox.measuredHeight)

            // Calculate the x-coordinate of the itemTitle: checkBox's right coordinate +
            // the icon's right margin.
            x += checkBox.measuredWidth + layoutParams.rightMargin

            // Add in the itemTitle's left margin.
            layoutParams = itemTitle.layoutParams as MarginLayoutParams
            x += layoutParams.leftMargin

            // Calculate the y-coordinate of the itemTitle: this ViewGroup's top padding +
            // the itemTitle's top margin
            y = paddingTop + layoutParams.topMargin

            // Layout the itemTitle.
            itemTitle.layout(x, y, x + itemTitle.measuredWidth, y + itemTitle.measuredHeight)

            // The itemDescription has the same x-coordinate as the itemTitle. So no more calculating there.

            // Calculate the y-coordinate of the itemDescription: the itemTitle's bottom coordinate +
            // the itemTitle's bottom margin.
            y += itemTitle.measuredHeight + layoutParams.bottomMargin
            layoutParams = itemDescription.layoutParams as MarginLayoutParams

            // Add in the subtitle's top margin.
            y += layoutParams.topMargin

            //Layout the itemDescription
            itemDescription.layout(x, y, x + itemDescription.measuredWidth, y + itemDescription.measuredHeight)
        }
    }
}

