package com.my.yang.draghelperdemo

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.activity_main2.*

class CustomHeaderActivity : Activity() {

    lateinit var adapter:MyAdapter

    var dataList:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        adapter = MyAdapter(R.layout.item_layout,dataList)

        for (i in 0..15){
            dataList.add("item$i")
        }

        rcvList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter.bindToRecyclerView(rcvList)

        srlView.setOnRefreshLoadMoreListener(object :OnRefreshLoadMoreListener{

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                Handler().postDelayed({
                    runOnUiThread {
                        Log.i("tag_ypf","111111111111111")
                        for (i in 0..8){
                            dataList.add("item$i")
                        }
                        adapter.notifyDataSetChanged()
                        srlView.finishLoadMore()
                    }
                },3000)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                Handler().postDelayed({
                    runOnUiThread{
                        Log.i("tag_ypf","22222222222222222")

                        srlView.finishRefresh()
                    }
                },3000)
            }

        })
    }
}

class MyAdapter(layoutId:Int,dataList:ArrayList<String>) :BaseQuickAdapter<String,BaseViewHolder>(layoutId,dataList){
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_Item,item)
    }

}
