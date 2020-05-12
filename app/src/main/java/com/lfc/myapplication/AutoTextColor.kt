package com.lfc.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_auto_text_color.*
import java.util.concurrent.TimeUnit


/*
   *@Author LFC
   *@Date 2020-4-28 14:07:46
   *@Remarks:
   */
class AutoTextColor : AppCompatActivity() {
//  private var StrValue = ""
    //  private var Type = 0


    private var mDisposable: Disposable? = null
    private var listcounts = mutableListOf<Int>()
    var styled: SpannableStringBuilder? = null
    var mWakeLock: WakeLock? = null
    var spanRed: ForegroundColorSpan? = null
    var lastTime: Long = 0
    var lastBegin = -1
    //是否记录过时间线
    var haveRecord = false
    public var readTimeMap: MutableMap<Int, Int> = mutableMapOf()
    var lastIndex = 0
    var currentCounts = 0

    companion object {
        fun EnterThis(context: Context, string: String = "", int: Int = 0) {
            var intent = Intent(context, AutoTextColor().javaClass)
            intent.putExtra("StrID", string)
            intent.putExtra("Type", int)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_text_color)
        //title = intent!!.getStringExtra("StrID")
        // Type = intent!!.getIntExtra("Type", 0)
//        initMVP()
        initView()
        //initData()
        getData()
    }


    fun test(): Disposable {
        return Flowable.interval(5, 5, TimeUnit.SECONDS)
            // 参数说明：
            // 参数1 = 第1次延迟时间；
            // 参数2 = 间隔时间数字；
            // 参数3 = 时间单位；
            // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

            /*
             * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
             * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
             **/
            .doOnNext {
                if (currentCounts >= AllLines) {
                    stopPolling()
                }
                var end = listcounts[currentCounts]
                Log.d("--lfc", "lastIndex:$lastIndex  end:$end ")
                updateText(lastIndex, end)
                autoScroll(end)
                lastIndex = end
                currentCounts++
            }.subscribe()


    }

    private fun stopPolling() {
        mDisposable?.dispose()
        mDisposable = null
    }

    @SuppressLint("InvalidWakeLockTag")
    private fun initView() {
        spanRed = ForegroundColorSpan(Color.RED)
        styled = SpannableStringBuilder(tv_content.text)

        tv_content.text = styled
        var vto = tv_content.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mLayout = tv_content.layout;
                AllLines = tv_content.lineCount
                getTextLineContetn(tv_content)
                tv_content.viewTreeObserver.removeOnGlobalLayoutListener(this); }
        })
        val pm =
            getSystemService(Context.POWER_SERVICE) as PowerManager
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag")
        tv_read.setOnClickListener {
            mDisposable = test()
            autoScroll(0)
        }
    }

    private fun getData() {

    }

    private fun reset() { //重置
        styled!!.removeSpan(spanRed)
        tv_content.text = styled
        sroll_news.smoothScrollTo(0, 0)
        lastBegin = -1
    }

    override fun onResume() {
        super.onResume()
        mWakeLock?.acquire()
    }

    override fun onPause() {
        super.onPause()
        mWakeLock?.release()
    }

    private fun updateText(beginPos: Int, endPos: Int) {
        if (lastBegin != beginPos) {
            if (beginPos == 0) { //第一次,记录开始时间
                lastTime = System.currentTimeMillis()
            }
            styled!!.removeSpan(spanRed)
            styled!!.setSpan(spanRed, 0, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv_content.text = styled
            lastBegin = beginPos
        }
    }


    // 自动滚屏相关代码

    // 自动滚屏相关代码
    var mLayout: Layout? = null
    var lastLine = 0
    var AllLines = 0

    private fun autoScroll(beginPos: Int) {
        val line = getLine(beginPos)
        //如果行数发生变化
        if (line != lastLine) { //保持3行的高度
            if (line >= 5) {
                sroll_news.smoothScrollTo(0, tv_content.top + mLayout?.getLineTop(line - 5)!!)
            }
            lastLine = line
        }
    }


    private fun getLine(staPos: Int): Int {
        var lineNumber = 0
        if (mLayout != null) {
            val line: Int = mLayout?.lineCount!!
            for (i in 0 until line - 1) {
                if (staPos <= mLayout?.getLineStart(i)!!) {
                    lineNumber = i
                    break
                }
            }
        }
        return lineNumber
    }

    fun getTextLineContetn(tv: TextView) {

        val text: String = tv?.text.toString()
        var start = 0
        var end: Int
        for (i in 0 until AllLines) {
            end = mLayout?.getLineEnd(i)!!
            val line = text.substring(start, end) //指定行的内容
            start = end
            val width = mLayout?.getLineWidth(i) //指定行的宽度
            listcounts.add(end)
            Log.e("test", "line $line,width $width")
        }

    }
}
