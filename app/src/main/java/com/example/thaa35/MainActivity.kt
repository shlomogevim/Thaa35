package com.example.thaa35

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.helper_view_layout.*

class MainActivity : AppCompatActivity() {

    lateinit var animationInAction: AnimationInAction
    lateinit var pref: GetAndStoreData
    lateinit var arrangeScreen: ArrangeScreen
    lateinit var buttonSpace: ButtonSpace
    lateinit var talkList: ArrayList<Talker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = GetAndStoreData(this)
        initAll()
        enterData()

        animationInAction.executeTalker()
    }

    private fun enterData(){
      //  updaeList()
   //    pref.saveCurrentPage(1)
//        val currentPage=pref.getCurrentPage()
//        talkList[currentPage].animNum=1000
//        pref.saveTalkingListInPref(talkList)
    }

    private fun updaeList() {
        var list=pref.getTalkingList(1)
        for (index in 0..list.size-1){
            with (list[index]){
                padding[0]=0
                padding[1]=0
                padding[2]=0
                padding[3]=0
            }
        }
        pref.saveTalkingList(list)
    }

    private fun initAll() {


        var showPosition = false
        pref.saveShowPosition(showPosition)

        if (showPosition){
            showPositionBtn.text="toTest"
        }else{
            showPositionBtn.text="toShow"

        }
        talkList = pref.getTalkingList(1)
      //  talkList = pref.getTalkingList(0)  // to Init all data
        arrangeScreen = ArrangeScreen(this)
        buttonSpace = ButtonSpace(this)
        animationInAction = AnimationInAction(this)
        buttonSpace.initButton()
        arrangeScreen.setLayoutShowMode()
        arrangeScreen.operateListView()
        buttonSpace.setShowPositionMode()
        var talker=pref.currentTalker()
        pref.saveLastTalker(talker)
    }


}


/* private fun getTalkList() {
     talkList = pref.createNewList()

     if (talkList.size == 0) {
         // createTalkingListFromFirestore()  //open tool->firebase->firestore see if all depen. ok
         //rebuilt project
         // run and wait for result
     }
     if (talkList.size == 0) {
         // !! must be in remarked becaseus it inteferring to the firebase
         talkList = pref.createTalkListFromTheStart()

     }
     pref.saveTalkingListInPref(talkList)
 }*/

/*@SuppressLint("RestrictedApi")
private fun waitToAnnimateEnded() {
    Utile.listener1 = { _, _ ->
        fab.visibility = View.VISIBLE
        fab1.visibility = View.VISIBLE
        ViewAnimator
            .animate(fab)
            .alpha(0f, 1f)
            .andAnimate(fab1)
            .alpha(0f, 1f)
            .duration(3500)
            .start()
    }
}*/

