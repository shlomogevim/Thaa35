package com.example.thaa35

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.Toast
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.helper_view_layout.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.util.concurrent.TimeUnit

class ButtonSpace(val context: Context) : View.OnClickListener {

    private val activity = context as Activity
    private val pref = GetAndStoreData(context)
    var animationInAction2= AnimationInAction2(context)

    override fun onClick(view: View) {
        when (view.id) {
            R.id.textRevBtn -> readAgainTextFile()
            R.id.newPageBtn -> enterNewPage()
            R.id.showPositionBtn -> changeShowPosition()
            R.id.toShowModeBtn -> animationInAction2.executeTalker2(0)
            R.id.plusAndMinusBtn -> changePlusMinusMode()
            R.id.saveButton -> saveIt()
            R.id.nextButton -> nextIt()
            R.id.previousButton -> previousIt()
            R.id.lastTalker_button -> retriveLastTalker()
            R.id.reSizeTextBtn -> minTextSize()
            R.id.fab -> nextIt()
            R.id.fab1 -> previousIt()
            else -> drawAnim()
        }
    }

    fun nextIt() {
        pref.saveLastTalker(pref.currentTalker())
        var page = pref.getCurrentPage()
        page++
        chkPage(page)
        animationInAction2.executeTalker2(0)
    }

    fun previousIt() {
        pref.saveLastTalker(pref.currentTalker())
        var page = pref.getCurrentPage()
        page--
        chkPage(page)
        animationInAction2.executeTalker2(0)
    }

    private fun chkPage(page: Int) {
        val talkList = pref.getTalkingList(1)
        var cu = page
        if (cu < 1 || cu >= talkList.size) cu = 1
        pref.saveCurrentPage(cu)
    }

   /* fun drawNewAnimation() {
        val page = pref.getCurrentPage()
        activity.tvPage.text = page.toString()
        animationInAction2.executeTalker2()

    }*/

    private suspend fun waitTillAnimationEnd() {
        val talker = pref.currentTalker()
        delay(talker.dur + 200)
        activity.fab.isClickable = true
        activity.fab1.isClickable = true
        animationInActionSign(0, 500)
       /* withContext(Main) {
            animationInActionSign(0, 600)
        }*/
    }

    fun animationInActionSign(ind: Int, dur: Long) {
        if (ind == 0) {
            ViewAnimator
                .animate(activity.tvPage)
                .backgroundColor(Color.RED, Color.GREEN)
                .duration(dur)
                .start()

        } else {
            ViewAnimator
                .animate(activity.tvPage)
                .backgroundColor(Color.GREEN, Color.RED)
                .duration(dur)
                .start()
        }
    }

    private fun oldVersion() {
        activity.fab.isClickable = false
        activity.fab1.isClickable = false
        animationInActionSign(1, 1000)
        // val size = pref.getLastTalker().takingArray.size
        val list = pref.getTalkingList(1)
        val index = pref.getCurrentPage()
        val size = list[index].takingArray.size


    }

    fun drawAnim() {
        val cu = getCurrentPage()
        activity.tvPage.text = cu.toString()
        animationInAction2.executeTalker2(0)
    }

    private fun changeShowPosition() {
        var showPosition=pref.getShowPosition()
        showPosition = !showPosition
        pref.saveShowPosition(showPosition)
        setShowPositionMode()
        if (showPosition) {
            activity.showPositionBtn.text = "toTest"
        } else {
            activity.showPositionBtn.text = "toShow"
        }
        animationInAction2.executeTalker2(0)
    }

    fun setShowPositionMode() {
        val showPosition=pref.getShowPosition()
        with(activity) {
            if (!showPosition) {
                plusAndMinusBtn.text = "+"
                lastTalker_button.text = "Last"
                saveButton.text = "Save"
                upper_layout.visibility = VISIBLE
                down_layout.visibility = VISIBLE

                textRevBtn.visibility = VISIBLE
                reSizeTextBtn.visibility = VISIBLE
                newPageBtn.visibility = VISIBLE
                toShowModeBtn.visibility = VISIBLE

                style_ListView.visibility = VISIBLE
                para_ListView.visibility = VISIBLE
                ttPara_listView.visibility = VISIBLE
                action_ListView.visibility = VISIBLE
                tvAnimatinKind.visibility = VISIBLE
                tvPage.visibility = VISIBLE
                fab.hide()
                fab1.hide()
            }
            if (showPosition) {
                down_layout.visibility = INVISIBLE
                // upper_layout.visibility = INVISIBLE
                textRevBtn.visibility = INVISIBLE
                reSizeTextBtn.visibility = INVISIBLE
                newPageBtn.visibility = INVISIBLE
                toShowModeBtn.visibility = INVISIBLE

                style_ListView.visibility = INVISIBLE
                para_ListView.visibility = INVISIBLE
                ttPara_listView.visibility = INVISIBLE
                action_ListView.visibility = INVISIBLE
                tvAnimatinKind.visibility = INVISIBLE
                tvPage.visibility = VISIBLE
                fab.show()
                fab1.show()
            }
        }
    }

    private fun readAgainTextFile() {
        var list = ArrayList<Talker>()

        CoroutineScope(IO).launch {
            val talkList = async { pref.getTalkingList(1) }
            val textTalkList = async { pref.createTalkListFromTheStart() }
            val talkList1 = async { textReRead(talkList.await(), textTalkList.await()) }
            pref.saveTalkingList(talkList1.await())
            list = talkList1.await()
            withContext(Main) {
                delay(1000)
                drawAnim()
            }
        }
    }

    fun cleanArr(arr: List<String>): List<String> {
        val ar = arrayListOf<String>()
        for (item in arr) {
            if (item != "") {
                ar.add(item)
            }
        }
        return ar
    }

    fun textReRead(
        talkList: ArrayList<Talker>,
        textTalkList: ArrayList<Talker>
    ): ArrayList<Talker> {
        for (index in 1..talkList.size - 1) {
            val st1 = textTalkList[index].taking
            var arr = st1.split("\n")
            arr = cleanArr(arr)
            /*val ar = arrayListOf<String>()
            for (item in arr) {
                if (item != "") {
                    ar.add(item)
                }
            }*/

            if (index > talkList.size) {
                var talk1 = textTalkList[index].copy()
                talkList.add(talk1)

            } else {

                talkList[index].takingArray = arr as ArrayList<String>
                talkList[index].taking = textTalkList[index].taking
            }

            if (index == textTalkList.size - 1) {
                talkList.dropLast(talkList.size - textTalkList.size)
                return talkList
            }
        }
        return talkList
    }


    private fun minTextSize() {
        updateLastTalker(0)
        val list = pref.getTalkingList(1)
        val index = pref.getCurrentPage()
        list[index].textSize = 12f  // for accsident of bigest text
        pref.saveTalkingList(list)
        drawAnim()
    }

    private fun enterNewPage() {

        var myDialog = AlertDialog.Builder(context)

        val input = EditText(context)
        myDialog.setView(input)
        myDialog.setTitle("Enter new page")
        myDialog.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val num = input.text.toString().toInt()
                pref.saveCurrentPage(num)
                drawAnim()
                return
            }

        })
        myDialog.setNegativeButton("CANCEL", null)
        myDialog.show()
    }

    private fun retriveLastTalker() {
        updateLastTalker(1)
        drawAnim()
    }

    private fun updateLastTalker(ind: Int) {
        val talkList = pref.getTalkingList(1)
        with(pref) {
            if (ind == 0) {
                // val sa = pref.currentTalk()
                saveLastTalker(currentTalker())
            } else {
                talkList[this@ButtonSpace.getCurrentPage()] = getLastTalker().copy()
            }
        }
    }

    fun saveIt() {
        val talkList = pref.getTalkingList(1)

        pref.saveTalkingList(talkList)
        Toast.makeText(context, "It's save Mr", Toast.LENGTH_SHORT).show()
    }

    fun updateTitleTalkerSituation() {
        with(pref.currentTalker()) {
            val text =
                "l=${takingArray.size}sty=$styleNum anim=$animNum size=${textSize.toInt()}" +
                        " bord=$borderWidth dur=$dur sw=$swingRepeat"
            activity.tvAnimatinKind.text = text
        }
        activity.tvPage.text = pref.getCurrentPage().toString()
    }


    fun nextItFab() {
        //  var cu1 = getAndStoreData.getCurrentPage()
        updateLastTalker(0)
        /*var cu = currentPage()
        cu++
        getAndStoreData.saveCurrentPage(cu)*/
        drawAnim()
    }


    private fun changePlusMinusMode() {
        with(activity.plusAndMinusBtn) {
            if (text == "+") {
                text = "-"
            } else {
                text = "+"
            }
        }
    }

    private fun time(st: String) {
//        endTime = System.nanoTime()
//        val interval = TimeUnit.MILLISECONDS.convert(endTime - statrTime, TimeUnit.NANOSECONDS)
//        Log.d("clima", st + " --> $interval ms")

    }


    fun letsPlay(v: View) {

        when (v.id) {
            R.id.textRevBtn -> readAgainTextFile()
            R.id.newPageBtn -> enterNewPage()
            R.id.showPositionBtn -> changeShowPosition()
            R.id.toShowModeBtn -> animationInAction2.executeTalker2(0)
            R.id.plusAndMinusBtn -> changePlusMinusMode()
            R.id.saveButton -> saveIt()
            R.id.nextButton -> nextIt()
            R.id.previousButton -> previousIt()
            R.id.lastTalker_button -> retriveLastTalker()
            R.id.reSizeTextBtn -> minTextSize()
            R.id.fab -> nextIt()
            R.id.fab1 -> previousIt()
            else -> drawAnim()

        }

        /* time("let play 1")
         if (!showPosition) {
             when (v.id) {
                 R.id.textRevBtn -> readAgainTextFile()
                 R.id.newPageBtn -> enterNewPage()
                 R.id.showPositionBtn -> changeShowPosition()
                 R.id.toShowModeBtn -> animationInAction.executeTalker(pref.currentTalk())
                 R.id.plusAndMinusBtn -> changePlusMinusMode()
               //  R.id.showPositionBtn -> drawAnim()
                 R.id.saveButton -> saveIt()
                 R.id.nextButton -> nextIt()
                 R.id.previousButton -> previousIt()
                 R.id.lastTalker_button -> retriveLastTalker()
                 R.id.reSizeTextBtn -> minTextSize()
 //                R.id.tvAnimatinKind -> tvAnimatinKind.visibility = View.VISIBLE
                 else -> drawAnim()

             }
             return
         }
         // var cu = getAndStoreData.getCurrentPage()
         if (showPosition) {
             when (v.id) {
                 R.id.fab -> nextItFab()
                 R.id.fab1 -> previousIt()
                 else -> drawAnim()
             }
         }
         time("let play 2")*/
    }



    fun getCurrentPage(): Int {
        val talkList = pref.getTalkingList(1)
        var cu = pref.getCurrentPage()
        if (cu < 1 || cu >= talkList.size) {
            cu = 1
            pref.saveCurrentPage(cu)
        }
        return cu
    }


    fun fabAnimation(ind: Int) {
        if (ind == 0) {
            ViewAnimator
                .animate(activity.fab)
                .alpha(0f)
                .andAnimate(activity.fab1)
                .alpha(0f)
                .duration(2000)
                .start()
        } else {
            ViewAnimator
                .animate(activity.fab)
                .alpha(0f, 1f)
                .andAnimate(activity.fab1)
                .alpha(0f, 1f)
                .duration(2000)
                .start()
        }
    }

    fun initButton() {
        with(activity) {
            showPositionBtn.setOnClickListener { onClick(showPositionBtn) }
            textRevBtn.setOnClickListener { onClick(textRevBtn) }
            newPageBtn.setOnClickListener { onClick(newPageBtn) }
            plusAndMinusBtn.setOnClickListener { onClick(plusAndMinusBtn) }
            saveButton.setOnClickListener { onClick(saveButton) }
            nextButton.setOnClickListener { onClick(nextButton) }
            previousButton.setOnClickListener { onClick(previousButton) }
            lastTalker_button.setOnClickListener { onClick(lastTalker_button) }
            reSizeTextBtn.setOnClickListener { onClick(reSizeTextBtn) }
            toShowModeBtn.setOnClickListener { onClick(toShowModeBtn) }
            fab.setOnClickListener { onClick(fab) }
            fab1.setOnClickListener { onClick(fab1) }
        }
        setShowPositionMode()
    }
}