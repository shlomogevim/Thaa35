package com.example.thaa35

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var animationInAction: AnimationInAction
    lateinit var getAndStoreData: GetAndStoreData
    lateinit var arrangeScreen: ArrangeScreen
    lateinit var buttonSpace: ButtonSpace
    lateinit var talkList: ArrayList<Talker>

    var showPosition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainProgram()
    }

    private fun mainProgram() {

        arrangeScreen = ArrangeScreen(this, showPosition)
        buttonSpace = ButtonSpace(this, showPosition)
        animationInAction = AnimationInAction(this)
        buttonSpace.initButton()
        getAndStoreData = GetAndStoreData(this)
        arrangeScreen.setLayoutShowMode()
        buttonSpace.setShowPositionMode()
        getTalkList()
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         getAndStoreData.saveCurrentPage(1)
        arrangeScreen.updateTitleTalkerSituation()
        animationInAction.executeTalker(talkList[1])
//waitToAnnimateEnded()
    }


    private fun getTalkList() {
        talkList = getAndStoreData.createNewList()

        if (talkList.size == 0) {
            // createTalkingListFromFirestore()  //open tool->firebase->firestore see if all depen. ok
            //rebuilt project
            // run and wait for result
        }
        if (talkList.size == 0) {
            // !! must be in remarked becaseus it inteferring to the firebase
            talkList = getAndStoreData.createTalkListFromTheStart()

        }
        getAndStoreData.saveTalkingListInPref(talkList)
    }

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
}
