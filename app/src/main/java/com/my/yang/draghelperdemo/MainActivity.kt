package com.my.yang.draghelperdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = "测试dragHelper"

        sample_text.setOnClickListener {
            startActivity(Intent(this@MainActivity,DragHelperActivity::class.java))
        }

        sample_tex2t.setOnClickListener {
            startActivity(Intent(this@MainActivity,Drag2Activity::class.java))
        }

        btMain.setOnClickListener {

            startActivity(Intent(this@MainActivity,CustomHeaderActivity::class.java))

        }
    }
}
