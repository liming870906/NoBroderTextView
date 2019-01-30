package com.liming.nobroder.nobrodertextview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liming.nobroder.nopaddingtextview.views.NoPaddingTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.findViewById<NoPaddingTextView>(R.id.nptv_name_1)
            .setText("测试文本：动态加载数据显示效果。动态加载数据显示效果。动态加载数据显示效果。")
    }
}
