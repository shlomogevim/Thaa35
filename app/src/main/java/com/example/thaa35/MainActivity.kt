package com.example.thaa35

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.god_layout.*
import kotlinx.android.synthetic.main.god_layout.godLayout

//import kotlinx.android.synthetic.main.layout.man_layout.view.*

class MainActivity : AppCompatActivity() {

    var conv: Convers? = null
    lateinit var getStoreData: GetAndStoreData
    lateinit var animationInAction: AnimationInAction
    lateinit var getAndStoreData: GetAndStoreData
    lateinit var arrangeLayout: ArrangeLayout
    lateinit var buttonSpace: ButtonSpace
    lateinit var talkList: ArrayList<Talker>

    var showPosition = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainProgram()
    }

    private fun mainProgram() {

        getStoreData = GetAndStoreData(this)
      //  animationInAction = AnimationInAction(godLayout,manLayout)
        animationInAction = AnimationInAction(this)

        /*  getAndStoreData = GetAndStoreData(view)
          getAndStoreData.saveCurrentFile(20)
          getAndStoreData.saveShowPosition(showPosition)

          getTalkList()

          arrangeLayout = ArrangeLayout(view)
          buttonSpace = ButtonSpace(view)

          backGroundConfigration()

          arrangeLayout.drawListView()

          arrangeLayout.operateListView()

          buttonSpace.initButton()

          arrangeLayout.setLayoutShowMode()
          waitToAnnimateEnded()

          animationInAction.executeTalker(talkC())*/
    }

    fun talkC() = talkList[getAndStoreData.getCurrentPage()]


}

/*

    @SuppressLint("RestrictedApi")
    private fun waitToAnnimateEnded() {
        Utile.listener1 = { it1, _ ->

            fab.visibility = View.VISIBLE
            fab1.visibility = View.VISIBLE
            ViewAnimator
                .animate(view?.fab)
                .alpha(0f, 1f)
                .andAnimate(view?.fab1)
                .alpha(0f, 1f)
                .duration(3500)
                .start()
        }

    }

    fun backGroundConfigration() {
        val animationDrawable = imageView.background as? AnimationDrawable
        animationDrawable?.setEnterFadeDuration(2000)
        animationDrawable?.setExitFadeDuration(4000)
        animationDrawable?.start()
    }

    private fun getTalkList() {
        talkList = getStoreData.createNewList()

        if (talkList.size == 0) {
            createTalkingListFromFirestore()  //open tool->firebase->firestore see if all depen. ok
            //rebuilt project
            // run and wait for result
        }
        if (talkList.size == 0) {
            // !! must be in remarked becaseus it inteferring to the firebase
              talkList=getStoreData.createTalkListFromTheStart()

        }
        getAndStoreData.saveTalkingListInPref(talkList)
    }

    fun createTalkingListFromFirestore(): ArrayList<Talker> {
        var talkList1 = ArrayList<Talker>()
        var jsonS: String
        /*val st="courses"
        val st1="11"
        val st2="name"*/
        val st = "talker1"
        val st1 = "3"
        val st2 = "main"
        var db = FirebaseFirestore.getInstance()
        db.collection(st).document(st1).get().addOnCompleteListener { task ->
            if (task.result!!.exists()) {
                jsonS = task.result?.getString(st2)!!
                val gson = Gson()
                val type = object : TypeToken<ArrayList<Talker>>() {}.type
                talkList1 = gson.fromJson(jsonS, type)
                getStoreData.saveTalkingListInPref(talkList1)
                Log.d("clima", " $jsonS")
            }
        }
        return talkList1
    }


    fun storeTalkingListFromFirestore(talkList: ArrayList<Talker>, index: Int) {

        // must transfer value with  key-value format
        val st = "talker1"
        val st1 = index.toString()
        val gson = Gson()
        val jsonS = gson.toJson(talkList)
        var db = FirebaseFirestore.getInstance()
        var talker = HashMap<String, Any>()
        talker.put("index", st1)
        jsonS?.let { talker.put("main", it) }
        db.collection(st).document(st1).set(talker)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Saving is succsses", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        context,
                        "Not Save because \${task.exception?.message",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


    }*/
