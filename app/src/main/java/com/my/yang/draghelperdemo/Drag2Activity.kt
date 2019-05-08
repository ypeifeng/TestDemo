package com.my.yang.draghelperdemo

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_drag2.*

class Drag2Activity : Activity() {

    lateinit var adapter: CommonAdapter

    var dataList:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag2)

        adapter = CommonAdapter(dataList,R.layout.item_layout)

//        var minTop = ConvertUtils.dp2px(100f)
//        var height = ScreenUtils.getScreenHeight() - minTop
//
//        var layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,height)
//
//        rcvDragViewList.layoutParams = layoutParams

        rcvDragViewList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcvDragViewList.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter.bindToRecyclerView(rcvDragViewList)

        adapter.setOnItemClickListener { adapter, view, position ->
            ToastUtils.showShort("${adapter.data[position]}")
        }

        postData()
    }

    private fun postData() {
        for (i in 0 until 100){
            dataList.add("$i")
        }
        adapter.notifyDataSetChanged()
    }
}

class CommonAdapter(data:ArrayList<String>,layoutId:Int) :BaseQuickAdapter<String,BaseViewHolder>(layoutId,data){

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_Item,item?:"")
    }

}
