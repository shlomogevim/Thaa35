package com.example.thaa35

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.helper_view_layout.*

class ArrangeScreen(val context: Context) {

    val activity = context as Activity

    private var styleList = arrayListOf<String>()
    private var paraList = arrayListOf<String>()
    private var ttParaList = arrayListOf<String>()
    private var actionList = arrayListOf<String>()

    private val pref = GetAndStoreData(context)
    private  val showPosition=pref.getShowPosition()
    private val animationInAction = AnimationInAction(context)

    private var interval = 0
    private var currentColor = "#stam"


   /* fun avtivateAnimation() {
      //  updateTitleTalkerSituation()
        animationInAction.executeTalker()
    }*/

    fun operateListView() {
        operateStyleLV()     //ListView in the left
        paramListView()      //ListView in the middle left
        ttParaListView()     //ListView in the middle right
        animationMovement()   //ListView in the middle right
       // updatePage(0)
      //  updateTitleTalkerSituation()
    }

    private fun operateStyleLV() {
        activity.style_ListView.setOnItemClickListener { _, _, position, _ ->
            val currentTalker = pref.currentTalk()
            if (position == 16) {     // ther is NB
                currentTalker.backExist = false
            } else {
                currentTalker.backExist = true
                currentTalker.styleNum = styleList[position].toInt()
            }
            upgradeTalker(currentTalker)
        }
    }
    private fun paramListView() {
        activity.para_ListView.setOnItemClickListener { _, _, position, _ ->
            translaePara(position)
        }
    }
    private fun ttParaListView() {
        activity.ttPara_listView.setOnItemClickListener { _, _, position, _ ->
            translaeTtPara(position)
            Toast.makeText(
                context,
                //  "Don't forget to select Para ListView to excute the operation",
                "Don't forget to select Para ", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun animationMovement() {  // list view in the right side
        createAnimLV()

        activity.action_ListView.setOnItemClickListener { _, _, position, _ ->
            var talker=pref.currentTalk()
            talker.animNum = actionList[position].toInt()
            upgradeTalker(talker)
        }
    }

    private fun translaePara(position: Int) {

        var talker = pref.currentTalk()
        val s = activity.plusAndMinusBtn.text
        val intv = if (s == "+") interval else -interval

        when (position) {

            7 -> {
                pref.saveCurrentPage(1)
                animationInAction.executeTalker()
            }
            8->enterNewPage()
                10->talker=pref.getLastTalker().copy()
            11 -> copySpecialTalker(1)
            15 -> talker.textSize = talker.textSize + intv
            16 -> talker.dur = talker.dur + intv
            19 -> changeTextColor(talker)
            20 -> changeBackColor(talker)
            21 -> changeBorderColor(talker)
            22 -> changeBorderWidth(talker, intv)
            23 -> talker.borderWidth = 0
            24 -> changeSwingRepeat(talker, intv)
            25 -> changeRadius(talker, intv)
            26->{
                pref.saveCurrentPage(1)
                animationInAction.executeTalker()
            }
        }
        if (position >9 && position<26) upgradeTalker(talker)
    }

       private fun upgradeTalker(talker: Talker) {
        var bo = true
        if (talker.textSize < 3) {
            talker.textSize = 3f
            Toast.makeText(context, "Text Size too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        if (talker.dur < 100) {
            talker.textSize = 100f
            Toast.makeText(context, "Duration too small", Toast.LENGTH_SHORT).show()
            bo = false
        }
        val style = findStyleObject(talker.styleNum)
        talker.colorBack = style.colorBack
        talker.colorText = style.colorText
        if (bo) {
            val talkList = pref.getTalkingList(1)
            var index=pref.getCurrentPage()
            talkList[index]=talker.copy()
            pref.saveTalkingList(talkList)
            animationInAction.executeTalker()
        }
    }

    fun copySpecialTalker(modelNum: Int) {
        val talker=pref.currentTalk()
        val spicalTalkList = arrayListOf(
            Talker(
                numTalker = 1, styleNum = 411, animNum = 61, textSize = 288f, dur = 3000
            ) // god "YES"
        )
        if (modelNum == 1 && talker.whoSpeake == "man") return
        var bo = true
        var i = 0
        while (bo && i < spicalTalkList.size) {

            if (spicalTalkList[i].numTalker == modelNum) {
                val spcialTalk = spicalTalkList[i]
                with(talker) {
                    styleNum = spcialTalk.styleNum
                    animNum = spcialTalk.animNum
                    textSize = spcialTalk.textSize
                    dur = spcialTalk.dur
                    val style = findStyleObject(spcialTalk.styleNum)
                    colorText = style.colorText
                    colorBack = style.colorBack
                }
                bo = false
            }
        }
    }

    fun initIt() {
        pref.saveCurrentPage(1)
       // moveTheAnimation()
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
                animationInAction.executeTalker()
                return
            }

        })
        myDialog.setNegativeButton("CANCEL", null)
        myDialog.show()
    }


    private fun chkNewData(talker: Talker):Talker {
        with(talker) {
            if (textSize > 300f) textSize = 300f
            if (textSize < 8f) textSize = 8f
            if (dur > 10000f) dur = 10000
            if (dur < 100f) dur = 100
            //  if (radius > 100f) radius = 100f
            if (radius < 2f) radius = 2f
            if (borderWidth > 70) borderWidth = 70
            if (borderWidth < 0) borderWidth = 0
            if (swingRepeat > 10) swingRepeat = 10
            if (swingRepeat < 0) swingRepeat = 0
        }
        return (talker)
    }

    private fun changeSwingRepeat(talker: Talker, intv: Int) {
        talker.swingRepeat = talker.swingRepeat + intv
    }

    private fun changeRadius(talker: Talker, intv: Int) {
        talker.radius = talker.radius + intv

    }

    private fun updateLastTalker(ind: Int) {
        var talker=pref.currentTalk()
        with(pref) {
            if (ind == 0) saveLastTalker(talker)
            else {
                talker = getLastTalker().copy()
            }
        }
    }

    private fun changeBorderColor(talker: Talker) {
        try {
            Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(context, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talker.borderColor = currentColor

    }

    private fun changeBorderWidth(talker: Talker, intv: Int) {
        talker.borderWidth = talker.borderWidth + intv

    }

    private fun changeBackColor(talker: Talker) {
        try {
            Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(context, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }

        talker.colorBack = currentColor

    }

    private fun changeTextColor(talker: Talker) {
        try {
            Color.parseColor(currentColor)
        } catch (iae: IllegalArgumentException) {
            Toast.makeText(context, "IIIigal color entery , try again", Toast.LENGTH_LONG).show()
            return
        }
        talker.colorText = currentColor

    }

    private fun translaeTtPara(position: Int) {
        when (position) {
            //14 -> selectColor()
            15 -> activity.colorNam_ET.visibility = VISIBLE
            16 -> interval = 0
            17 -> interval = 1
            18 -> interval = 2
            19 -> interval = 3
            20 -> interval = 4
            21 -> interval = 5
            22 -> interval = 6
            23 -> interval = 7
            24 -> interval = 8
            25 -> interval = 9
            26 -> interval = 10
            27 -> interval = 11
            28 -> interval = 12
            29 -> interval = 13
            30 -> interval = 14
            31 -> interval = 15
            32 -> interval = 20
            33 -> interval = 50
            34 -> interval = 100
            35 -> interval = 1000
            36 -> interval = 2000
            37 -> interval = 3000
            38 -> interval = 5000
            39 -> currentColor = "#ffffff"
            40 -> currentColor = "#000000"
            41 -> currentColor = "#8e0000"
            411 -> currentColor = "#ad1457"
            42 -> currentColor = "#9c27b0"
            43 -> currentColor = "#1565c0"
            44 -> currentColor = "#03a9f4"
            45 -> currentColor = "#009688"
            46 -> currentColor = "#00701a"
            47 -> currentColor = "#9ccc65"
            48 -> currentColor = "#a0af22"
            49 -> currentColor = "#fdd835"
            50 -> currentColor = "#ffc107"
            51 -> currentColor = "#ff9800"
            52 -> currentColor = "#ff5722"
            53 -> currentColor = "#4b2c20"
            54 -> currentColor = "#9e9e9e"
            55 -> currentColor = "#90a4ae"

            else -> {
                interval = 0
            }
        }
    }



    private fun startListFromTheBeginning(){
        pref.saveCurrentPage(1)
     //  moveTheAnimation()
    }


   /* private fun moveTheAnimation() {
        updateTitleTalkerSituation()
        animationInAction.executeTalker(pref.currentTalk())
    }*/

    private fun findStyleObject(index: Int): StyleObject {
        var style1 = StyleObject()
        var bo = true
        var i = 0
        while (bo && i < Helper.Page.styleArray.size) {

            if (Helper.Page.styleArray[i].numStyleObject == index) {
                style1 = Helper.Page.styleArray[i]
                bo = false
            }
            i++
        }
        if (bo) style1 = Helper.Page.styleArray[10]
        return style1
    }

   /* fun currentPage(): Int {
        val talkList = pref.getTalkingListFromPref(1)
        var cu = pref.getCurrentPage()
        if (cu < 1 || cu >= talkList.size) {
            cu = 1
            pref.saveCurrentPage(cu)
        }
        return cu
    }*/
   /* private fun updatePage(ind: Int) {
        val currentPage = pref.getCurrentPage()
        val lastPage = pref.getLastPage()
        if (ind == 0) {
            //lastTalker = talkList[counterStep].copy()
            pref.saveLastPage(currentPage)
        } else {
            //talkList[counterStep] = lastTalker.copy()
            if (lastPage > 1) {
                pref.saveLastPage(lastPage - 1)
            }
            if (currentPage > 1) {
                pref.saveCurrentPage(currentPage - 1)
            }
        }
    }*/

    private fun drawListView() {

       createStyleLV()
        createParaList()
        createTtParaTV()
       createAnimLV()
    }

    private fun createStyleLV() {
        Helper.Page.createBasicStyle()
        for (i in 0..15) {
            styleList.add("-")
        }
        styleList.add("NB")
        for (item in Helper.Page.styleArray) {
            val st = item.numStyleObject.toString()
            styleList.add(st)
        }
        for (i in 0..15) {
            styleList.add("-")
        }
        val adapter10 = ArrayAdapter<String>(context, R.layout.mytext, styleList)
        activity.style_ListView.adapter = adapter10
        activity.style_ListView.setSelection(15)

    }

    private fun createParaList() {
        for (i in 0..5) {
            paraList.add("-")
        }
        val list = arrayListOf(
            "-",
            "Start",
            "Page",
            "-",
            "Last Talker",
            "CopyTalk1",
            "-",
            "-",
            "-",
            "TextSize",
            "Duration",
            "-",
            "-",
            "Text Color",
            "Back Color",
            "Bord Color",
            "Bord Line",
            "No Bord",
            "Swing Re.",
            "Radius",
            "Start"
        )
        paraList.addAll(list)

        for (i in 0..20) {
            paraList.add("-")
        }

        val adapter10 = ArrayAdapter<String>(context, R.layout.mytext, paraList)
        activity.para_ListView.adapter = adapter10
        activity.para_ListView.setSelection(5)
    }

    private fun createTtParaTV() {
        for (i in 0..13) {
            ttParaList.add("-")
        }
        val list = getTtParaList()
        ttParaList.addAll(list)
        for (i in 0..20) {
            ttParaList.add("-")
        }
        val adapter11 =
            ArrayAdapter<String>(context, R.layout.mytext, ttParaList)
        activity.ttPara_listView.adapter = adapter11
        activity.ttPara_listView.setSelection(15)
    }

    private fun getTtParaList(): List<String> = arrayListOf(
        "Piker Color",
        "Color Nun",
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "20",
        "50",
        "100",
        "1000",
        "2000",
        "3000",
        "5000",
        "C-White",
        "C-Black",
        "C-Red",
        "C-Pink",
        "C-Purple",
        "C-Blue",
        "C-LBlue",
        "C-Teal",
        "C-Green",
        "C-LGreen",
        "C-Lime",
        "C-Yellow",
        "C-Amber",
        "C-Orange",
        "C-DOrange",
        "C-Brown",
        "C-Gray",
        "C-BGray"
    )

    private fun createAnimLV() {
        for (i in 0..15) {
            actionList.add("-")
        }
        val list = arrayListOf(
            "100","1200","1201","1001","101","102","103","104","105","106","107","108","109",
            "110","111","10", "11", "12", "13", "14", "15",
            "20", "21", "22", "23", "24", "25",
            "30", "31", "32", "33", "34", "35",
            "40", "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54", "55", "506",
            "60", "61", "62", "63", "64", "65","100"
        )
        actionList.addAll(list)
        for (i in 0..15) {
            actionList.add("-")
        }
        val adapter1 =
            ArrayAdapter<String>(context, R.layout.mytext, actionList)
        activity.action_ListView.adapter = adapter1
        activity.action_ListView.setSelection(5)
    }

    private fun backGroundConfigaration() {
        val imageV = activity.findViewById<ImageView>(R.id.imageView)
        val animationDrawable = imageV.background as? AnimationDrawable
        animationDrawable?.setEnterFadeDuration(2000)
        animationDrawable?.setExitFadeDuration(4000)
        animationDrawable?.start()
    }
    fun setLayoutShowMode() {
        with(activity) {
            if (!showPosition) {
                drawListView()
                plusAndMinusBtn.text = "+"
                lastTalker_button.text = "Last"
                saveButton.text = "Save"
                upper_layout.visibility = VISIBLE
                style_ListView.visibility = VISIBLE
                para_ListView.visibility = VISIBLE
                ttPara_listView.visibility = VISIBLE
                action_ListView.visibility = VISIBLE
                tvAnimatinKind.visibility = VISIBLE
                fab.hide()
                fab1.hide()

            }
            if (showPosition) {
                backGroundConfigaration()
                down_layout.visibility = INVISIBLE
                upper_layout.visibility = INVISIBLE
                style_ListView.visibility = INVISIBLE
                para_ListView.visibility = INVISIBLE
                ttPara_listView.visibility = INVISIBLE
                action_ListView.visibility = INVISIBLE
                tvAnimatinKind.visibility = INVISIBLE
                fab.show()
                fab1.show()
            }
        }
    }

}