package com.lfc.myapplication.objectbox

import android.app.Activity
import com.lfc.myapplication.R
import com.lfc.myapplication.objectbox.bean.UserBean
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder


/**
 * Created by LFC
 * on 2019/8/13.
 *
 * GreenDao 的使用
 *
 */

class Adapter_GreenDaoList(
    private val baseContext: Activity,
    layoutId: Int,
    datas: MutableList<UserBean>
) : CommonAdapter<UserBean>(baseContext, layoutId, datas) {
    private var list_data: MutableList<UserBean> = mutableListOf()

    init {
        list_data = datas

    }


    override fun convert(holder: ViewHolder, commonDataM: UserBean, position: Int) {
        holder.setText(
            R.id.tv_note,
            "id ${commonDataM.id}+ name ${commonDataM.strNmae} skill${commonDataM.strSkill} "
        )// 日期

        /* holder.itemView.setOnClickListener {
             //            LUtils.showToask(baseContext, position.toString())
             Toast.makeText(baseContext, position.toString(), Toast.LENGTH_SHORT).show()
         }*/

    }

    fun RefreshAll(list_datas: MutableList<UserBean>) {
        this.list_data = list_datas
//        list_data.clear()
//        list_data.addAll(list_datas)
        notifyDataSetChanged()
    }
}
