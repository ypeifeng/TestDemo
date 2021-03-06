package com.my.yang.draghelperdemo.refresh_head_foot_view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.my.yang.draghelperdemo.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import kotlinx.android.synthetic.main.custom_food_view.view.*


class CustomFootView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),RefreshHeader {

    var state:RefreshState? = null //记录状态

    var anim:AnimationDrawable?=null //正在刷新时的加载动画

    init {
        initView(context)
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }



    /**
     * 尺寸定义初始化完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     * @param kernel RefreshKernel 核心接口（用于完成高级Header功能）
     * @param height HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {

    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {

    }

    /**
     * 手指释放之后的持续动画（会连续多次调用，用于实时控制动画关键帧）
     * @param percent 下拉的百分比 值 = offset/headerHeight (0 - percent - (headerHeight+maxDragHeight) / headerHeight )
     * @param offset 下拉的像素偏移量  0 - offset - (headerHeight+maxDragHeight)
     * @param headerHeight Header的高度
     * @param maxDragHeight 最大拖动高度
     */
    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {

    }

    override fun getView(): View {
        return this
    }

    override fun setPrimaryColors(vararg colors: Int) {

    }

    /**
     * 开始动画（开始刷新或者开始加载动画）
     * @param layout RefreshLayout
     * @param height HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        Log.i("ypf_customHeadView","onStartAnimator")
        imgPullUpView.setImageResource(R.drawable.pull_up_anim)
        anim = imgPullUpView.drawable as AnimationDrawable?
        anim?.start()
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        Log.i("ypf_customHeadView","onFinish")
        imgPullUpView.setImageResource(R.drawable.pull_up_anim)
        anim = imgPullUpView.drawable as AnimationDrawable?
        anim?.stop()
        return 500//延迟500毫秒之后再弹回
    }


    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        state = newState
        when (newState) {
            RefreshState.None, RefreshState.PullDownToRefresh -> {
                //"下拉开始刷新"
                Log.i("ypf_customHeadView","onStateChanged :  下拉开始刷新")

            }
            RefreshState.Refreshing -> {
                Log.i("ypf_customHeadView","onStateChanged :  正在刷新")

            }
            RefreshState.ReleaseToRefresh -> {
                Log.i("ypf_customHeadView","onStateChanged :  释放立即刷新")
            }
        }
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
//        var percentInt = Math.floor(percent*10.toDouble()).toInt()
//        if (percentInt in 1..8 && state==RefreshState.PullDownToRefresh){
//            Log.i("ypf_customHeadView","percent*10:   $percentInt")
//            imgPullUpView.setImageResource(context.resources.getIdentifier("anim$percentInt","mipmap",AppUtils.getAppPackageName()))
//        }
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.custom_food_view,this)
    }



}