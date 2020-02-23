package com.example.thaa35

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.helper_view_layout.*

class MainActivity : AppCompatActivity() {

    lateinit var animationInAction2: AnimationInAction2
    lateinit var pref: GetAndStoreData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = GetAndStoreData(this)
        animationInAction2=AnimationInAction2(this)
        initAll()
        enterData()
        animationInAction2.executeTalker2()
    }

    private fun initAll() {
        var showPosition = false
        pref.saveShowPosition(showPosition)

        if (showPosition) showPositionBtn.text = "toTest"
                    else showPositionBtn.text = "toShow"

    // var   talkList = pref.getTalkingList(1)
      var  talkList = pref.getTalkingList(0)  // to Init all data
    }

    private fun enterData() {
        //  updaeList()
        //    pref.saveCurrentPage(1)
//        val currentPage=pref.getCurrentPage()
//        talkList[currentPage].animNum=1000
//        pref.saveTalkingListInPref(talkList)
    }

    private fun updaeList() {
        var list = pref.getTalkingList(1)
        for (index in 0..list.size - 1) {
            with(list[index]) {
                padding[0] = 0
                padding[1] = 0
                padding[2] = 0
                padding[3] = 0
                numTalker = index + 1
            }
        }
        pref.saveTalkingList(list)
    }

}

