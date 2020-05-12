package com.lfc.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lfc.myapplication.objectbox.SqlTest

/*
   *@Author LFC
   *@Date 2020-4-28 9:14:41
   *@Remarks:
   */
class MainActivity : AppCompatActivity() {
//  private var StrValue = ""
    //  private var Type = 0

    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, MainActivity().javaClass)
            intent.putExtra("StrID", string)
            intent.putExtra("Type", int)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //title = intent!!.getStringExtra("StrID")
        // Type = intent!!.getIntExtra("Type", 0)
//        initMVP()
        initView()
        //initData()
        getData()
    }

    private fun initView() {
        SqlTest.EnterThis(this)

    }

    private fun getData() {

    }
}
