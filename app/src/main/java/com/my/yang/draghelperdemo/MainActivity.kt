package com.my.yang.draghelperdemo

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.my.yang.draghelperdemo.base.BaseActivity
import com.my.yang.draghelperdemo.draghelper.DragHelp2Activity
import com.my.yang.draghelperdemo.draghelper.DragHelperActivity
import com.my.yang.draghelperdemo.refresh_head_foot_view.CustomHeaderActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    var sampleDragHelper:String = "简易的dragHelper"
    var listDragHelper:String = "list的DragHelper"
    var customHeadFootView:String = "自定义头足布局"

    var dataList:ArrayList<String> = ArrayList()
    lateinit var adapter:MainAdapter

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        dataList.add(sampleDragHelper)
        dataList.add(listDragHelper)
        dataList.add(customHeadFootView)
    }

    override fun initView() {
        adapter = MainAdapter(R.layout.item_main_list,dataList)
        rcvMain.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.bindToRecyclerView(rcvMain)
    }

    override fun initEvent() {

        adapter.setOnItemChildClickListener { adapter, view, position ->
            var text = adapter.data[position] as String
            when(text){
                sampleDragHelper->{
                    startActivity(Intent(this@MainActivity,DragHelperActivity::class.java))
                }
                listDragHelper->{
                    startActivity(Intent(this@MainActivity, DragHelp2Activity::class.java))
                }
                customHeadFootView->{
                    startActivity(Intent(this@MainActivity, CustomHeaderActivity::class.java))
                }
            }
        }

    }

    override fun postData() {

    }

}

class MainAdapter(layoutId: Int, dataList: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutId, dataList) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.btItemMain,item)
        helper?.addOnClickListener(R.id.btItemMain)
    }

}
