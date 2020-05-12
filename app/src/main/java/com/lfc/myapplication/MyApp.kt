package com.lfc.myapplication

import android.app.Application
import io.objectbox.BoxStore
import io.objectbox.example.kotlin.ObjectBox


/**
 * Created by LFC
on 2020/4/28.
 */
class MyApp : Application() {
    private var boxStore: BoxStore? = null

    companion object Constants {
        const val TAG = "--lfc"
    }


    override fun onCreate() {
        super.onCreate()


        ObjectBox.init(this)
    }


}