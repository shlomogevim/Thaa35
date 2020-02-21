package com.example.thaa35

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.thaa35.Const.Companion.ANIM1
import com.example.thaa35.Const.Companion.ANIM2
import com.example.thaa35.Const.Companion.ANIM3
import com.example.thaa35.Const.Companion.ANIM4
import com.example.thaa35.Const.Companion.ASSEETS_FILE
import com.example.thaa35.Const.Companion.CURRENT_PAGE
import com.example.thaa35.Const.Companion.FILE_NUM
import com.example.thaa35.Const.Companion.FONTS
import com.example.thaa35.Const.Companion.LASTTALKER
import com.example.thaa35.Const.Companion.LAST_PAGE
import com.example.thaa35.Const.Companion.PREFS_NAME
import com.example.thaa35.Const.Companion.SHOWPOSITION
import com.example.thaa35.Const.Companion.TALKLIST
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class GetAndStoreData(val context: Context) : AppCompatActivity() {

    var myPref = context.getSharedPreferences(PREFS_NAME, 0)

    fun saveCurrentPage(index: Int) {myPref.edit().putInt(CURRENT_PAGE, index).apply()}
    fun saveShowPosition(bo: Boolean) {myPref.edit().putBoolean(SHOWPOSITION, bo).apply()}

    fun getCurrentPage(): Int = myPref.getInt(CURRENT_PAGE, 1)
    fun getCurrentFile(): Int = myPref.getInt(FILE_NUM, 1)
    fun getShowPosition(): Boolean = myPref.getBoolean(SHOWPOSITION, true)

    fun saveAnim1(index: Int) {myPref.edit().putInt(ANIM1, index).apply()}
    fun saveAnim2(index: Int) {myPref.edit().putInt(ANIM2, index).apply()}
    fun saveAnim3(index: Int) {myPref.edit().putInt(ANIM3, index).apply()}
    fun saveAnim4(index: Int) {myPref.edit().putInt(ANIM4, index).apply()}
    fun saveFonts(index: Int) {myPref.edit().putInt(FONTS, index).apply()}
    fun getAnim1(): Int = myPref.getInt(ANIM1, 0)
    fun getAnim2(): Int = myPref.getInt(ANIM2, 0)
    fun getAnim3(): Int = myPref.getInt(ANIM3, 0)
    fun getAnim4(): Int = myPref.getInt(ANIM4, 0)
    fun getFonts(): Int = myPref.getInt(FONTS, 1)

    fun currentTalker():Talker{
        val list=getTalkingList(1)
        var index=getCurrentPage()
        if (list.size<=index) index=1
        saveCurrentPage(index)
        return list[index]
    }

    fun saveTalkingList(talkingList: ArrayList<Talker>) {
        val gson = Gson()
        val tagNum=getCurrentFile()
        val jsonString = gson.toJson(talkingList)
       // myPref.edit().putString(TALKLIST+tagNum.toString(), jsonString).apply()
        myPref.edit().putString(TALKLIST, jsonString).apply()
    }
    fun saveLastTalker(lastTalker: Talker) {
        val gson = Gson()
        val jsonString = gson.toJson(lastTalker)
        myPref.edit().putString(LASTTALKER, jsonString).apply()
    }

    fun getLastTalker():Talker{
        var talker=Talker()
        var jsonS=myPref.getString(LASTTALKER,null)
        if (jsonS!=null){
            val gson=Gson()
            val type = object : TypeToken<Talker>() {}.type
            talker = gson.fromJson(jsonS, type)
        }
        return talker
    }

    fun getTalkingList(ind: Int): ArrayList<Talker> {
        val talkList: ArrayList<Talker>
        val gson = Gson()
        val jsonString = myPref.getString(TALKLIST, null)

        if (ind == 0 || jsonString == null) {
            talkList = createTalkListFromTheStart()
            saveTalkingList(talkList)
            saveCurrentPage(1)
            saveLastTalker(talkList[1])

        } else {
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList = gson.fromJson(jsonString, type)
        }
        return talkList
    }

    fun createTalkListFromTheStart(): ArrayList<Talker> {
        var talkList1 = arrayListOf<Talker>()
        val ADAM = "-אדם-"
        val GOD = "-אלוהים-"
        val currenteFile = "text/text" + ASSEETS_FILE + ".txt"

        var countItem = 0
        var text = context.assets.open(currenteFile).bufferedReader().use {
            it.readText()
        }
        text = text.replace("\r", "")
        var list1 = text.split(ADAM)

        var talker = Talker()

        talkList1.add(countItem, talker)
        var i = 0

        for (element in list1) {
            //  if (element != "" && element.length > 25) {
            if (element != "") {
                i++
                var list2 = element.split(GOD)
                var st1 = improveString(list2[0])
                var st2 = improveString(list2[1])
                if (st1.isNullOrEmpty() || st2.isNullOrEmpty()) {
                    return talkList1
                }
                countItem++
                talker = Talker()
                with(talker) {
                    whoSpeake = "man"
                    taking = st1.trim()
                    numTalker = countItem
                    var arr = st1.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }

                    backExist=true
                    borderColor="#000000"
                    borderWidth=0
                    styleNum=50
                    swingRepeat=0
                    colorText = "#fdd835"
                    colorBack = "#574339"
                    textSize=28f
                    animNum = 100
                    dur=3000
                    radius=25f
                }
                talkList1.add(talker)
                countItem++
                talker = Talker()
                with(talker) {
                    whoSpeake = "god"
                    talker.taking = st2.trim()
                    talker.numTalker = countItem
                    var arr = st2.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    backExist=true
                    borderColor="#000000"
                    borderWidth=0
                    styleNum=51
                    swingRepeat=0
                    colorText = "#574339"
                    colorBack = "#fdd835"
                    textSize=28f
                    animNum = 100
                    dur=3000
                    radius=25f
                }
                talkList1.add(talker)
            }
        }
        return talkList1
    }

    private fun improveString(st: String) = st.substring(1, st.length - 1)




    fun saveLastPage(index: Int) {myPref.edit().putInt(LAST_PAGE, index).apply()}
    fun getLastPage(): Int = myPref.getInt(LAST_PAGE, 1)
    fun saveCurrentFile(index: Int) {myPref.edit().putInt(FILE_NUM, index).apply()}
    private fun createTalkArray(jsonString: String?) {
        var talkList: ArrayList<Talker>
        //  Log.d("clima",jsonString)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Talker>>() {}.type
        talkList = gson.fromJson(jsonString, type)
        Log.d("clima", "${talkList[19].taking}")
    }

    fun createNewList(): ArrayList<Talker> {
        var talkList1 = ArrayList<Talker>()
        val tagNum=getCurrentFile()

        // var jsonS =  myPref.getString(TALKLIST+tagNum.toString(), null)
        var jsonS =  myPref.getString(TALKLIST, null)
        if (jsonS != null) {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList1 = gson.fromJson(jsonS, type)
            //saveTalkingListInPref(talkList1)
        }
        return talkList1
    }

    fun saveJsonString(jsonS: String?) {
        myPref.edit().putString(TALKLIST, jsonS).apply()
    }

    fun getJsonArryFromPref( ): ArrayList<Talker> {
        var list= ArrayList<Talker>()
        var jsonS: String?
        jsonS = myPref.getString(TALKLIST, null)
        if (!jsonS.isNullOrEmpty()){
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            list = gson.fromJson(jsonS, type)
        }
        return list
    }


    fun createTalkList(): ArrayList<Talker> {
        var talkList: ArrayList<Talker>
        val jsonString = myPref.getString(TALKLIST, null)

        // val jsonString = intent.getStringExtra(JSONSTRING)
        // val jsonString = intent.getStringExtra(JSONSTRING)
        if (jsonString == "none" || jsonString == "") {
            talkList = getTalkingList(0)
            saveTalkingList(talkList)

        } else {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList = gson.fromJson(jsonString, type)
            saveTalkingList(talkList)
        }
        return talkList
    }

    private fun decodebase64(input:String):Bitmap{
        val decodeByte=Base64.decode(input,0)
        val bit=BitmapFactory.decodeByteArray(decodeByte,0,decodeByte.size)
        return bit
    }

    private fun encodeToBase64(image:Bitmap):String{
        val immage=image
        val baos=ByteArrayOutputStream()
        immage.compress(Bitmap.CompressFormat.PNG,100,baos)
        val b=baos.toByteArray()
        val imageEncoded=Base64.encodeToString(b,Base64.DEFAULT)
        Log.d("clima","imageEncode->$imageEncoded")
        return imageEncoded
    }
}