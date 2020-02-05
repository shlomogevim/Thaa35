package com.example.thaa35

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.ImageView

class NewLayoutPreparation(val activity: Activity, val mainLayout:View) {
    //val activity=context as Activity

    fun backGroundConfigaration() {
        val imageV=activity.findViewById<ImageView>(R.id.imageView)
        val animationDrawable = imageV.background as? AnimationDrawable
        animationDrawable?.setEnterFadeDuration(2000)
        animationDrawable?.setExitFadeDuration(4000)
        animationDrawable?.start()
    }

}