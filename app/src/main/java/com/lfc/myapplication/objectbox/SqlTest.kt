package com.lfc.myapplication.objectbox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.lfc.myapplication.R
import com.lfc.myapplication.WrapContentLinearLayoutManager
import com.lfc.myapplication.objectbox.bean.UserBean
import com.lfc.myapplication.objectbox.bean.UserBean_
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import io.objectbox.Box
import io.objectbox.example.kotlin.ObjectBox
import kotlinx.android.synthetic.main.activity_sql_test.*
import kotlin.random.Random


/*
   *@Author LFC
   *@Date 2020-4-28 18:10:17
   *@Remarks:
   */
class SqlTest : AppCompatActivity() {
//  private var StrValue = ""
    //  private var Type = 0

    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, SqlTest().javaClass)
            intent.putExtra("StrID", string)
            intent.putExtra("Type", int)
            context.startActivity(intent)
        }
    }

    private var sizeLong: Long = 0
    private var list_data = mutableListOf<UserBean>()
    private var adapterGreendao: Adapter_GreenDaoList? = null
    private var userEntityBox: Box<UserBean>? = null
    private var baseContext: Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_test)
        //title = intent!!.getStringExtra("StrID")
        // Type = intent!!.getIntExtra("Type", 0)
        baseContext = this
        initView()
        initData()
        getData()
    }

    private fun initData() {
        userEntityBox = ObjectBox.boxStore.boxFor(UserBean::class.java)
    }

    private fun initView() {
        rlv?.apply {
            var wllm = WrapContentLinearLayoutManager(baseContext)
            layoutManager = wllm
            itemAnimator = DefaultItemAnimator()
            adapterGreendao =
                Adapter_GreenDaoList(baseContext!!, R.layout.item_greendao, list_data)
            adapterGreendao?.setOnItemClickListener(object :
                MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemLongClick(
                    p0: View?,
                    p1: RecyclerView.ViewHolder?,
                    p2: Int
                ): Boolean {
                    var userIndex = list_data[p2]
                    userEntityBox?.remove(userIndex)
                    searchALLDBData()
                    NotifyRlv()
                    return false
                }

                override fun onItemClick(p0: View?, p1: RecyclerView.ViewHolder?, p2: Int) {
                    var userIndex = list_data[p2]
                    userIndex.strSkill = "哈塞给.."
                    userEntityBox?.put(userIndex)
                    searchALLDBData()
                    NotifyRlv()

                }

            })
            adapter = adapterGreendao
        }
        btn02_gd.setOnLongClickListener {
            userEntityBox?.removeAll()
            true
        }
    }

    private fun getData() {

    }

    fun DoClick(view: View) {
        when (view.id) {
            R.id.btn01_gd -> {
//增
                addDBData()
                searchALLDBData()
                NotifyRlv()
            }
            R.id.btn02_gd -> {
//删
//                delDBData()
//                NotifyRlv()
//                searchALLDBData()
//                NotifyRlv()
            }
            R.id.btn03_gd -> {
//改


            }
            R.id.btn04_gd -> {
//查
                searchDBData()
                NotifyRlv()

            }
            else -> {
            }
        }


    }

    /**
     * 查询数据
     */
    private fun searchDBData() {
//        userEntityBox?.query { order(UserBean_.strID) }
        val listResult: MutableList<UserBean>? =
            userEntityBox?.query()?.equal(UserBean_.strID, "2")?.build()?.find()
        list_data.clear()
        listResult?.let { list_data.addAll(it) }

    }

    private fun searchALLDBData() {
        val listResult: MutableList<UserBean>? = userEntityBox?.query()?.build()?.find()
        list_data.clear()
        listResult?.let { list_data.addAll(it) }

    }

    /**
     * 删除数据
     */
    private fun delDBData() {
//        userEntityBox?.remove()

    }

    /**
     * 新增数据
     */
    private fun addDBData() {
        var randomSize = Random.nextInt(6) + 1
        Log.d("--lfc", "randomSize: $randomSize")
        for (index in 0..randomSize) {
            var userD = UserBean()
            userD.strNmae = getRandomString2(10)
            userD.age = index * Random.nextInt(10)
            userD.strID = index.toString()
            userEntityBox?.put(userD)
        }

    }

    private fun NotifyRlv() {
        adapterGreendao?.RefreshAll(list_data)
    }

    /**
     * 第二种方法
     */
    fun getRandomString2(allsize: Int): String {
        var length = 0
        if (allsize == 0) {
            length = java.util.Random().nextInt(10) + 5
        } else
            length = allsize
        //产生随机数
        val random = java.util.Random()
        val sb = StringBuffer()
        //循环length次
        for (i in 0 until length) {
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            val number = random.nextInt(3)
            var result: Long = 0
            when (number) {
                //如果number产生的是数字0；
                0 -> {
                    //产生A-Z的ASCII码
                    result = Math.round(Math.random() * 25 + 65)
                    //将ASCII码转换成字符
                    sb.append(result.toChar().toString())
                }
                1 -> {
                    //产生a-z的ASCII码
                    result = Math.round(Math.random() * 25 + 97)
                    sb.append(result.toChar().toString())
                }
                2 ->
                    //产生0-9的数字
                    sb.append(java.util.Random().nextInt(10).toString())
            }
        }
        return sb.toString()
    }
}
