package com.lfc.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test_icons.*


/*
   *@Author LFC
   *@Date 2020-4-28 11:23:37
   *@Remarks:
   */
class TestIcons : AppCompatActivity() {
//  private var StrValue = ""
    //  private var Type = 0

    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, TestIcons().javaClass)
            intent.putExtra("StrID", string)
            intent.putExtra("Type", int)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_icons)
        //title = intent!!.getStringExtra("StrID")
        // Type = intent!!.getIntExtra("Type", 0)

        initView()
        //initData()
        getData()
    }

    private fun initView() {
        val iconfont = Typeface.createFromAsset(assets, "iconfont.ttf")
        tv_icon.typeface = iconfont
    }

    private fun getData() {

    }
}
