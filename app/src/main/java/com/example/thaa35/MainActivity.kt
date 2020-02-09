package com.example.thaa35

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var animationInAction: AnimationInAction
    lateinit var pref: GetAndStoreData
    lateinit var arrangeScreen: ArrangeScreen
    lateinit var buttonSpace: ButtonSpace
    lateinit var talkList: ArrayList<Talker>


    var showPosition = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAll()
        enterData()
        animationInAction.executeTalker(talkC())
    }
    private fun talkC() = talkList[currentPage()]
    fun currentPage(): Int {
        var cu = pref.getCurrentPage()
        if (cu < 1 || cu >= talkList.size) {
            cu = 1
            pref.saveCurrentPage(cu)
        }
        return cu
    }

    private fun enterData(){
 //       pref.saveCurrentPage(1)
//        val currentPage=pref.getCurrentPage()
//        talkList[currentPage].animNum=1000
//        pref.saveTalkingListInPref(talkList)
    }

    private fun initAll() {
        pref = GetAndStoreData(this)
        talkList = pref.getTalkingListFromPref(1)
        arrangeScreen = ArrangeScreen(this, showPosition)
        buttonSpace = ButtonSpace(this, showPosition)
        animationInAction = AnimationInAction(this, showPosition)
        buttonSpace.initButton()
        arrangeScreen.setLayoutShowMode()
        arrangeScreen.operateListView()
        buttonSpace.setShowPositionMode()
    }

    private fun mainProgram() {






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

