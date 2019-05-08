package com.my.yang.draghelperdemo.draghelper.widght

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_drag2.view.*

class MyDragViewLayout(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    lateinit var dragHelper: ViewDragHelper

    lateinit var recyclerView: RecyclerView
    
    var maxTop:Int = 0
    
    var minTop:Int = 0

    var currentTop:Int = 0

    init {
        dragHelper = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child === dragView
            }

            //每次移动竖直方向的移动都会回调此方法,top为y轴的值(相对于viewGroup,从0开始),dy为每次移动的偏移量
            //此方法返回拖拽的子view竖直方向需要达到的值
            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                Log.i("ypf123456", "clampViewPositionVertical()   minTop:  $minTop     maxTop:  $maxTop")
                return when {
                    top<minTop -> minTop
                    top>maxTop -> maxTop
                    else -> top
                }
            }

            //同上
            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                Log.i("ypf123456", "clampViewPositionHorizontal()   left:  $left")
                return 0
            }

            // 当前被捕获的View释放之后回调,xvel:竖直方向的速度,yvel:水平方向的速度
            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                if (releasedChild === dragView) {
                    //settleCapturedViewAt()方法将被拖拽的子view移动到指定位置,内部使用Scroller实现,因此需要调用invalidate()方法,去回调computeScroll()方法
                    var top = when {
                        yvel<0 -> minTop
                        yvel>0 -> maxTop
                        else -> {
                            when {
                                releasedChild.top<(measuredHeight/2) -> minTop
                                releasedChild.top>=(measuredHeight/2) -> maxTop
                                else -> maxTop
                            }
                        }
                    }
                    Log.i("ypf123456", "releasedChild.top:  ${releasedChild.top}")

                    dragHelper.settleCapturedViewAt(0, top)
                    invalidate()
                }
            }

            //当拖拽状态改变时回调
            override fun onViewDragStateChanged(state: Int) {
                super.onViewDragStateChanged(state)
            }

            /*
            * 拖拽过程中回调
            * left: 被拖拽的x坐标
            * top:被拖拽的y坐标
            * dx:x轴偏移
            * dy:y轴偏移
            * */
            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)

                currentTop+=dy
                requestLayout()

            }

            override fun getViewVerticalDragRange(child: View): Int {
                return measuredHeight - child.measuredHeight
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return measuredWidth - child.measuredWidth

            }
        })
        // 设置左边缘可以被Drag,设置此方法后,触摸左边缘
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    private var oldY :Float = 0f

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        if (ev.action== MotionEvent.ACTION_DOWN){
            oldY = ev.y
        } else if (ev.action == MotionEvent.ACTION_MOVE){ //如果是移动手势
            var newY = ev.y
            if (oldY<newY){ //下滑
                if (recyclerView.canScrollVertically(-1)){ //表示recyclerView未到顶部
                    return false
                }
            }
//            else{ //上滑
//                if (recyclerView.canScrollVertically(1)){ //表示recyclerView未到底部
//                    return false
//                }
//            }
            oldY = newY
        }

        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        //dragHelper.continueSettling方法是用来判断当前被捕获的子View是否还需要继续移动，类似Scroller的computeScrollOffset方法一样
        if (dragHelper.continueSettling(true)) {
            //需要移动时刷新view
            invalidate()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        


        var childAt = dragView.getChildAt(0)
        if (childAt is RecyclerView){
            recyclerView = childAt
        }

        viewTreeObserver.addOnGlobalLayoutListener {
            minTop = topView.measuredHeight
            maxTop = measuredHeight - 60 * 6 * ScreenUtils.getScreenDensity().toInt()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        Log.i("ypf123456", "onLayout四角坐标:  0,${dragView.top+currentTop},${dragView.measuredWidth},$measuredHeight")
        dragView.layout(0,dragView.top+currentTop,dragView.measuredWidth,dragView.top+currentTop+measuredHeight)
    }
}
