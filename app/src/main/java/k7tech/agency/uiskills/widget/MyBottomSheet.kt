package k7tech.agency.uiskills.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import k7tech.agency.uiskills.R

class MyBottomSheet @JvmOverloads constructor(context: Context?, attrs: AttributeSet?, defStyle: Int = 0) :
    ViewGroup(context) {

    /**
     *The color used to dim the mainView when the bottom sheet is expanded.
     */
    private val dimBackgroundColor = 0x99000000

    /**
     * How far the bottom sheet is from its default position. Range [0,0.3] where 0 = collapsed, 0.3 = expanded
     */
    private var expandedOffset = 0f

    /**
     * Drawable used to give the bottom sheet a shadow on its top edge.
     */
    private var shadowDrawable: Drawable? = null

    /**
     * Triggered when dimmed part of the layout is triggered
     */
    private lateinit var onDimClickListener: OnClickListener

    /**
     * The paint used to dim the main layout when the bottom sheet is visible
     */
    private val dimPaint = Paint()

    /**
     * State of the bottom sheet.
     */
    enum class BottomSheetState {
        SCROLLING,
        EXPANDED,
        HIDDEN
    }

    private val anchorPoint = DEFAULT_ANCHOR_POINT
    private val rect = Rect()

    private var bottomSheetResId: Int = -1
    private var shadowHeight = 0f
    private var sheetState = BottomSheetState.HIDDEN
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private lateinit var mainView: View
    private lateinit var bottomSheetView: View
    private lateinit var viewDragHelper: ViewDragHelper

    init {
        context?.withStyledAttributes(attrs, R.styleable.MyBottomSheet) {
            bottomSheetResId = getResourceId(R.styleable.MyBottomSheet_bottomSheetView, defStyle)
            viewDragHelper = ViewDragHelper.create(this@MyBottomSheet, 1.0f, DragHelperCallback())
            shadowDrawable = ResourcesCompat.getDrawable(resources, R.drawable.shadow, null)
            //convert to pixels.
            shadowHeight = DEFAULT_SHADOW_HEIGHT * (context.resources.displayMetrics.density)

            setWillNotDraw(false)
        }
    }

    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is MarginLayoutParams && super.checkLayoutParams(p)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        // draw the small shadow above the bottom sheet.
        with(bottomSheetView) {
            shadowDrawable?.setBounds(left, (top - shadowHeight).toInt(), right, top)
        }
        if (canvas != null) shadowDrawable?.draw(canvas)
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        val count = canvas?.save()
        val result: Boolean

        if (bottomSheetView != child) {
            // if main view, clip it against the bottom edge of the screen so that when the bottom sheet expands,only the
            // area above the sheet is dimmed.
            canvas?.getClipBounds(rect)
            canvas?.clipRect(rect)

            result = super.drawChild(canvas, child, drawingTime)

            if (expandedOffset > 0) {
                // extract the alpha
                val alpha = (dimBackgroundColor.toInt() and -0x1000000) ushr 24
                // increase or decrease the alpha's intensity/ opacity. Depends on whether the window is sliding up or down.
                val intensity = (alpha * expandedOffset).toInt()
                // set the color. This basically selects the white color and applies the intensity as the alpha
                val color = intensity shl 24 or (dimBackgroundColor.toInt() and 0xffffff)
                dimPaint.color = color

                canvas?.drawRect(rect, dimPaint)
            }
        } else {
            result = super.drawChild(canvas, child, drawingTime)
        }

        if (count != null) canvas.restoreToCount(count)
        return result
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return when (p is MarginLayoutParams) {
            true -> MarginLayoutParams(p)
            false -> MarginLayoutParams(p)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bottomSheetView = findViewById(bottomSheetResId)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        expandedOffset = when (sheetState) {
            BottomSheetState.EXPANDED -> anchorPoint
            BottomSheetState.HIDDEN -> 0.0f
            else -> 0.0f
        }

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            var childTop = top

            if (child == bottomSheetView) childTop = computeTopPosition(expandedOffset)

            val childBottom = childTop + measuredHeight
            val childLeft = left
            val childRight = childLeft + measuredWidth

            child.layout(childLeft, childTop, childRight, childBottom)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        mainView = getChildAt(0)
        bottomSheetView = getChildAt(1)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            //Naively make the child measurements exactly as the imposed height and size of the displayWindow
            //In real apps all edge cases should be handled e.g. considering margins and padding etc etc.
            val childWidthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY)
            val childHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY)

            child.measure(childWidthSpec, childHeightSpec)
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_DOWN -> {
                motionTouchEventX = ev.x
                motionTouchEventY = ev.y
                return false
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                // check to see if the clicked region is the dimmed region
                if (expandedOffset > 0 && !isViewUnder(
                        bottomSheetView,
                        motionTouchEventX.toInt(),
                        motionTouchEventY.toInt()
                    )
                ) {
                    playSoundEffect(SoundEffectConstants.CLICK)
                    onDimClickListener.onClick(this)
                    return true
                }
            }
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev)
    }

    fun getSheetState(): BottomSheetState = sheetState

    /**
     * Provides an on click for the portion of the main view that is dimmed. The listener is not
     * triggered if the bottom sheet is in state HIDDEN
     **/
    fun setOnDimClickListener(listener: OnClickListener) {
        onDimClickListener = listener
    }

    override fun computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    fun setSheetState(state: BottomSheetState) {
        setInternalSheetState(state)

        when (state) {
            BottomSheetState.EXPANDED -> smoothSlide(anchorPoint)
            BottomSheetState.HIDDEN -> smoothSlide(0.0f)
            BottomSheetState.SCROLLING -> throw IllegalStateException("Sheet cannot be scrolling")
        }
    }

    /**
     *  Compute the current top position of the bottom sheet given and offset
     *  @param expandedOffset: current offset
     */
    private fun computeTopPosition(expandedOffset: Float): Int {
        val currentSheetHeight = (expandedOffset * bottomSheetView.measuredHeight).toInt()
        return measuredHeight - currentSheetHeight
    }

    /**
     *  Compute the current offSet of the bottom sheet given the top position
     *  @param topPosition: current top position
     */
    private fun computeExpandedOffset(topPosition: Int): Float {
        val topWhenHidden = computeTopPosition(0.0f)

        return if (topWhenHidden > topPosition) {
            (topWhenHidden.toFloat() - topPosition.toFloat()) / bottomSheetView.measuredHeight.toFloat()
        } else 0.0f

    }

    // I don't quite get this method yet. Copied the logic from the android framework
    private fun isViewUnder(view: View, x: Int, y: Int): Boolean {
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)

        val parentLocation = IntArray(2)
        getLocationOnScreen(parentLocation)

        val screenX = parentLocation[0] + x
        val screenY = parentLocation[1] + y

        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.width
                && screenY >= viewLocation[1] && screenY < viewLocation[1] + view.height
    }

    //Called whenever the sheet is scrolling to the expanded or hidden position
    private fun onSheetMoved(topPosition: Int) {
        expandedOffset = computeExpandedOffset(topPosition)
        setInternalSheetState(BottomSheetState.SCROLLING)
        invalidate()
    }

    /**
     * Smoothly animate bottomSheetView to the position with the specified
     * @param offset
     */
    private fun smoothSlide(offset: Float) {
        val topPosition = computeTopPosition(offset)

        if (viewDragHelper.smoothSlideViewTo(bottomSheetView, bottomSheetView.left, topPosition)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private fun setInternalSheetState(state: BottomSheetState) {
        if (sheetState == state) return
        sheetState = state
    }

    private inner class DragHelperCallback : ViewDragHelper.Callback() {
        // the view to drag or scroll.
        override fun tryCaptureView(child: View, pointerId: Int): Boolean = child == bottomSheetView

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            onSheetMoved(top)
        }

        override fun onViewDragStateChanged(state: Int) {
            // when the sheet has now settled. update the internal state.
            if (viewDragHelper.viewDragState == ViewDragHelper.STATE_IDLE) {
                when (expandedOffset > 0.0) {
                    true -> setInternalSheetState(BottomSheetState.EXPANDED)
                    false -> setInternalSheetState(BottomSheetState.HIDDEN)
                }
            }
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return bottomSheetView.measuredHeight
        }
    }
}

private const val DEFAULT_ANCHOR_POINT = 0.3f
private const val DEFAULT_SHADOW_HEIGHT = 4.0f
