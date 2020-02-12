package com.example.thaa35

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.TextView
import com.github.florent37.viewanimator.ViewAnimator

object Utile {
    var wi: Float = Resources.getSystem().displayMetrics.widthPixels.toFloat()
    var hi: Float = Resources.getSystem().displayMetrics.heightPixels.toFloat()
    var start = 0L
    var end = 0L
    var pointLeftDown=Point((-wi / 2).toInt(), hi.toInt())
    var pointRightDown=Point((wi / 2).toInt(), hi.toInt())
    var pointLeftUp=Point((-wi / 2).toInt(), -hi.toInt())
    var pointRightUp=Point((wi / 2).toInt(), -hi.toInt())
    var listener1: ((item: Int, myTime: Long) -> Unit)? = null

    fun moveScale100( talker: Talker, arr: ArrayList<TextView?>) {
        if (talker.whoSpeake=="man") {
            val linesNum = talker.takingArray.size
            for (index in 1..linesNum) {
                itemMoveScaleMan(talker, index, arr[index - 1]!!)
            }
        }else{
            moveScale2000(talker,arr)
        }
    }

    private fun itemMoveScaleMan(talker: Talker, ind: Int, textView: TextView) {
        val tv=initTextView(textView)
        when (ind){
            1-> basicMoveAndScale(tv, pointLeftUp, talker.dur, ind)
            2-> basicMoveAndScale(tv, pointRightUp, talker.dur, ind)
            3-> basicMoveAndScale(tv, pointLeftUp, talker.dur, ind)
            4-> basicMoveAndScale(tv, pointRightUp, talker.dur, ind)
            5-> basicMoveAndScale(tv, pointLeftUp, talker.dur, ind)
            6-> basicMoveAndScale(tv, pointRightUp, talker.dur, ind)
        }
    }


    fun moveScale2000( talker: Talker, arr: ArrayList<TextView?>) {
        val linesNum = talker.takingArray.size
        for (index in 1..linesNum) {
            itemMoveScale(talker, index, arr[index - 1]!!)
        }
    }


    private fun itemMoveScale(talker: Talker, ind: Int, textView: TextView) {
        val tv=initTextView(textView)
        when (ind){
            1-> basicMoveAndScale(tv, pointLeftDown, talker.dur, ind)
            2-> basicMoveAndScale(tv, pointRightDown, talker.dur, ind)
            3-> basicMoveAndScale(tv, pointLeftDown, talker.dur, ind)
            4-> basicMoveAndScale(tv, pointRightDown, talker.dur, ind)
            5-> basicMoveAndScale(tv, pointLeftDown, talker.dur, ind)
            6-> basicMoveAndScale(tv, pointRightDown, talker.dur, ind)
        }
    }

    private fun basicMoveAndScale(textView:TextView,point:Point,dur:Long,ind:Int){
        ViewAnimator
            .animate(textView).scale(0f, 1f).translationX(point.x.toFloat(), 0f).translationY(point.y.toFloat(), 0f)
            .duration(dur)
            .start().onStop {
                end = System.currentTimeMillis() - start
                listener1?.invoke(ind, end)
            }

    }
    private fun basicMoveAndScale1(textView:TextView,point:Point,dur:Long,ind:Int){
        ViewAnimator
            .animate(textView)
            .scale(0f, 1f)
            .translationX(point.x.toFloat(), 0f)
            .translationY(point.y.toFloat(), 0f)
            //.interpolator(BounceInterpolator()) 
            .interpolator(AccelerateInterpolator(1.2f))
            .duration(dur)
            .start().onStop {
                end = System.currentTimeMillis() - start
                listener1?.invoke(ind, end)
            }

    }
    private fun initTextView(textView:TextView):TextView{
        textView.visibility = View.VISIBLE
        textView.scaleX = 1f
        textView.scaleY = 1f
        return textView
    }



    fun getCordinateAndSpineNew(ind: Int): Array<Float> {
        var x01 = 0f
        var y01 = 0f
        var x02 = 0f
        var y02 = 0f
        var rotate = 360f;

        when (ind) {
            1 -> {
                x01 = -wi / 2
                y01 = -hi
                rotate = 720f * 2
            }
            2 -> {
                x01 = wi / 2
                y01 = -hi
                rotate = -720f * 2
            }
            3 -> {
                x01 = -wi / 2
                y01 = 0f
                rotate = 360f
            }
            4 -> {
                x01 = wi / 2
                y01 = 0f
                rotate = -360f
            }
            5 -> {
                x01 = -wi / 2
                y01 = hi
                rotate = 720f * 2
            }
            6 -> {
                x01 = wi / 2
                y01 = hi
                rotate = -720f * 2
            }
        }
        return arrayOf(x01, y01, x02, y02, rotate)
    }


    fun moveSwingNew(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {
        val linesNum = talker.takingArray.size
        for (index in 1..linesNum) {
            itemMoveSwingNew(selector, talker, index, arr[index - 1]!!)
        }
    }

    private fun itemMoveSwingNew(selector: Int, talker: Talker, ind: Int, textView: TextView) {

        var arr = arrayOf<Float>()
        textView.visibility = View.VISIBLE
        textView.scaleX = 1f
        textView.scaleY = 1f


        if (talker.whoSpeake == "man") {
            arr = getCordinateAndSpineNew(ind)
        } else {
            arr = getCordinateAndSpineNew(5)
        }

        if (talker.swingRepeat == 0) {
            ViewAnimator
                .animate(textView).scale(0f, 1f).translationX(arr[0], 0f).translationY(arr[1], 0f)
                .duration(talker.dur)
                .start().onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }
        } else {
            ViewAnimator
                .animate(textView).scale(0f, 1f).translationX(arr[0], 0f).translationY(arr[1], 0f)
                .duration(talker.dur)
                .thenAnimate(textView).swing().repeatCount(talker.swingRepeat).start().onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        }
    }


    fun chageBackgroundColor(ind: Int, textView: TextView, dur: Long) {
        if (ind == 0) {
            ViewAnimator
                .animate(textView)
                .backgroundColor(Color.RED, Color.GREEN)
                .duration(dur)
                .start()

        } else {
            ViewAnimator
                .animate(textView)
                .backgroundColor(Color.GREEN, Color.RED)
                .duration(dur)
                .start()

        }
    }

    fun item_scale(ind: Int, textView: TextView, dur: Long) {

        if (ind > -7) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }
        }
    }

    fun item_scale_swing(ind: Int, textView: TextView, dur: Long, rep: Int) {

        if (rep == 0) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }
        } else {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(textView)
                .swing()
                .repeatCount(rep)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        }
    }


    fun item_move_swing(talker: Talker, ind: Int, textView: TextView, dur: Long, rep: Int) {
        var arr = getCordinateAndSpine(ind)
        if (talker.whoSpeake == "god") {
            arr = getCordinateAndSpine(5)
        }

        textView.scaleX = 1f
        textView.scaleY = 1f

        textView.visibility = View.VISIBLE
        if (rep == 0) {
            ViewAnimator
                .animate(textView)
                .scale(1f)
                .duration(1)
                .thenAnimate(textView)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        } else {

            ViewAnimator
                .animate(textView)
                .scale(1f)
                .duration(1)
                .thenAnimate(textView)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .thenAnimate(textView)
                .swing()
                .repeatCount(rep)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        }
    }

    fun item_move_swing100(talker: Talker, ind: Int, textView: TextView, dur: Long, rep: Int) {
        var arr = arrayOf<Float>()
        textView.visibility = View.VISIBLE
        // textView.setBackgroundColor(Color.GREEN)
        textView.scaleX = 1f
        textView.scaleY = 1f


        if (talker.whoSpeake == "man") {
            arr = getCordinateAndSpine(ind)
        } else {
            arr = getCordinateAndSpine(5)

        }
        textView.visibility = View.VISIBLE
        if (rep == 0) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)


                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        } else {

            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)

                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .duration(dur)
                .thenAnimate(textView)
                .swing()
                .repeatCount(rep)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }

        }
    }

    fun item_move_scale(ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)

        ViewAnimator
            .animate(textView)
            .scale(0f, 1f)
            .translationX(arr[0], 0f)
            .translationY(arr[1], 0f)
            .duration(dur)
            .start()
            .onStop {
                end = System.currentTimeMillis() - start
                listener1?.invoke(ind, end)
            }

    }

    fun item_move_scale_rotate(ind: Int, textView: TextView, talker: Talker) {
        var arr = getCordinateAndSpine(ind)
        if (ind == talker.takingArray.size) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[4])
                .duration(talker.dur)
                .thenAnimate(textView)
                .rotation(-arr[4])
                .duration(1)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(ind, end)
                }
        } else {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[4])
                .duration(talker.dur)
                .thenAnimate(textView)
                .rotation(-arr[4])
                .duration(1)
                .start()
        }
    }


    fun move_swing(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {

        val linesNum = talker.takingArray.size
        if (selector == 10) {
            for (index in 1..linesNum) {
                item_move_swing(talker, index, arr[index - 1]!!, talker.dur, talker.swingRepeat)
            }
        }
        if (selector == 100) {
            for (index in 1..linesNum) {
                item_move_swing100(talker, index, arr[index - 1]!!, talker.dur, talker.swingRepeat)
            }
        }
        with(talker) {
            if (selector == 11) {
                if (linesNum > 1) {
                    for (index in 1..linesNum) {
                        arr[index - 1]?.let {
                            if (index == 1) {
                                // item_move(index, it, dur)
                                item_move_swing(talker, index, it, dur, 0)

                            } else {
                                item_move_swing(talker, index, it, dur, swingRepeat)

                            }
                        }
                    }
                } else {
                    item_move_swing(talker, 1, arr[0]!!, dur, swingRepeat)

                }
            }
            if (selector == 12) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 2) {
                            //item_move(index, it, dur)
                            item_move_swing(talker, index, it, dur, 0)
                        } else {
                            item_move_swing(talker, index, it, dur, swingRepeat)

                        }
                    }
                }
            }
            if (selector == 13) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 3) {
                            // item_move(index, it, dur)
                            item_move_swing(talker, index, it, dur, 0)

                        } else {
                            item_move_swing(talker, index, it, dur, swingRepeat)

                        }
                    }
                }
            }
            if (selector == 14) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 4) {
                            //  item_move(index, it, dur)
                            item_move_swing(talker, index, it, dur, 0)
                        } else {
                            item_move_swing(talker, index, it, dur, swingRepeat)

                        }
                    }
                }
            }
            if (selector == 15) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 5) {
                            //item_move(index, it, dur)
                            item_move_swing(talker, index, it, dur, 0)
                        } else {
                            item_move_swing(talker, index, it, dur, swingRepeat)

                        }
                    }
                }
            }
        }

    }

    fun scale_swing(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {
        start = System.currentTimeMillis()
        with(talker) {
            val linesNum = takingArray.size
            if (selector != 20 && swingRepeat < 1) swingRepeat = 1
            if (selector == 20) {
                for (index in 0 until linesNum) {
                    arr[index]?.let {
                        item_scale_swing(index + 1, it, dur, swingRepeat)
                    }
                }
            }

            if (selector == 21) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index == 1) {
                            // item_scale(index, it, dur)
                            item_scale_swing(index, it, dur, 0)

                        } else {
                            item_scale_swing(index, it, dur, swingRepeat)

                        }
                    }
                }
            }
            if (selector == 22) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 2) {
                            //item_scale(index, it, dur)
                            item_scale_swing(index, it, dur, 0)
                        } else {
                            item_scale_swing(index, it, dur, swingRepeat)

                        }
                    }
                }
            }
            if (selector == 23) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 3) {
                            //  item_scale(index, it, dur)
                            item_scale_swing(index, it, dur, 0)
                        } else {
                            item_scale_swing(index, it, dur, swingRepeat)
                        }
                    }
                }
            }
            if (selector == 24) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 4) {
                            // item_scale(index, it, dur)
                            item_scale_swing(index, it, dur, 0)
                        } else {
                            item_scale_swing(index, it, dur, swingRepeat)

                        }
                    }
                }
            }
            if (selector == 25) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 5) {
                            //  item_scale(index, it, dur)
                            item_scale_swing(index, it, dur, 0)
                        } else {
                            item_scale_swing(index, it, dur, swingRepeat)

                        }
                    }
                }
            }
        }
    }

    fun move_scale(selector: Int, arr: ArrayList<TextView?>, dur: Long) {
        start = System.currentTimeMillis()
        if (selector == 30) {
            for (index in 0 until arr.size) {
                arr[index]?.let { item_scale(index + 1, it, dur) }
            }
        }

        if (selector == 31) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 0) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 32) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 1) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 33) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 2) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 34) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 3) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
        if (selector == 35) {
            for (index in 0 until arr.size) {
                arr[index]?.let {
                    if (index > 4) {
                        item_move_scale(index + 1, it, dur)
                    } else {
                        item_scale(index + 1, it, dur)
                    }
                }
            }
        }
    }

    fun move_scale_rotate(selector: Int, talker: Talker, arr: ArrayList<TextView?>) {
        start = System.currentTimeMillis()
        with(talker) {
            val linesNum = takingArray.size
            if (selector == 40) {
                for (index in 0 until linesNum) {
                    arr[index]?.let { item_move_scale_rotate(index + 1, it, talker) }
                }
            }

            if (selector == 41) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index == 1) {
                            item_scale(index, it, dur)
                        } else {
                            item_move_scale_rotate(index, it, talker)

                        }
                    }
                }
            }

            if (selector == 42) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 2) {
                            item_scale(index, it, dur)
                        } else {
                            item_move_scale_rotate(index, it, talker)

                        }
                    }
                }
            }
            if (selector == 43) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 3) {
                            item_scale(index, it, dur)
                        } else {
                            item_move_scale_rotate(index, it, talker)

                        }
                    }
                }
            }
            if (selector == 44) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 4) {
                            item_scale(index, it, dur)
                        } else {
                            item_move_scale_rotate(index, it, talker)

                        }
                    }
                }
            }
            if (selector == 45) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= 5) {
                            item_scale(index, it, dur)
                        } else {
                            item_move_scale_rotate(index, it, talker)

                        }
                    }
                }
            }


            if (selector == 46) {
                for (index in 1..linesNum) {
                    arr[index - 1]?.let {
                        if (index <= takingArray.size - 1) {
                            item_move_scale_rotate(index, it, talker)
                        } else {
                            if (swingRepeat < 1) swingRepeat = 1
                            item_scale_swing(index, it, dur, swingRepeat)
                        }
                    }
                }
            }
        }
    }

    fun apeareOneAfterAnother(arr: ArrayList<TextView?>, talker: Talker) {
        start = System.currentTimeMillis()
        val dur = talker.dur
        if (arr.size == 1) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(1, end)
                }
        }

        if (arr.size == 2) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(2, end)
                }
        }
        if (arr.size == 3) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                //.duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                //.duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(3, end)
                }
        }
        if (arr.size == 4) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(4, end)
                }
        }
        if (arr.size == 5) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(5, end)
                }
        }
        if (arr.size == 6) {
            ViewAnimator
                .animate(arr[0])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[1])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[2])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[3])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[4])
                .scale(0f, 1f)
                .duration(dur)
                .thenAnimate(arr[5])
                .scale(0f, 1f)
                .duration(dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(6, end)
                }
        }

    }

    fun apeareOneAfterAnotherAndSwing(arr: ArrayList<TextView?>, talker: Talker) {
        start = System.currentTimeMillis()
        with(talker) {
            if (arr.size == 1) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .swing()
                    .duration(dur)
                    .thenAnimate(arr[0])
                    .repeatMode(swingRepeat)
                    .swing()
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(1, end)
                    }
            }

            if (arr.size == 2) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .repeatMode(swingRepeat)
                    .swing()
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(2, end)
                    }
            }
            if (arr.size == 3) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .repeatMode(swingRepeat)
                    .swing()
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(3, end)
                    }
            }
            if (arr.size == 4) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .repeatMode(swingRepeat)
                    .swing()
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(4, end)
                    }
            }
            if (arr.size == 5) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[4])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[4])
                    .repeatMode(swingRepeat)
                    .swing()
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(5, end)
                    }
            }
            if (arr.size == 6) {
                ViewAnimator
                    .animate(arr[0])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[1])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[2])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[3])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[4])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[5])
                    .scale(0f, 1f)
                    .duration(dur)
                    .thenAnimate(arr[5])
                    .swing()
                    .repeatCount(swingRepeat)
                    .start()
                    .onStop {
                        end = System.currentTimeMillis() - start
                        listener1?.invoke(6, end)
                    }
            }
        }
    }


    fun godAppearFromTwoPlaces(
        ind: Int, talker: Talker,
        arr: ArrayList<TextView?>,
        arr1: ArrayList<TextView?>,
        arr2: ArrayList<TextView?>
    ) {
        start = System.currentTimeMillis()
        if (ind == 0) {
            ViewAnimator
                .animate(arr[0])
                .translationX(-wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .andAnimate(arr1[0])
                .translationX(wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .duration(talker.dur)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(1, end)
                }
        }
        if (ind == 1) {
            arr[0]?.setBackgroundColor(Color.TRANSPARENT)         // begining just the text
            arr1[0]?.setBackgroundColor(Color.TRANSPARENT)
            ViewAnimator
                .animate(arr[0])
                .translationX(-wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .andAnimate(arr1[0])
                .translationX(wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .duration(talker.dur)
                .thenAnimate(arr2[0])
                .scale(0f, 1f)
                .duration(1)
                .thenAnimate(arr2[0])
                .alpha(0f, 1f)
                .duration(3000)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(2, end)
                }

        }
        if (ind == 2) {
            arr[0]?.setBackgroundColor(Color.TRANSPARENT)         // only the text appear
            arr1[0]?.setBackgroundColor(Color.TRANSPARENT)
            ViewAnimator
                .animate(arr[0])
                .translationX(-wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .andAnimate(arr1[0])
                .translationX(wi / 2, 0f)
                .translationY(hi, 0f)
                .scale(0f, 1f)
                .duration(talker.dur)
                // .thenAnimate(arr[0])
                // .backgroundColor(Color.parseColor(talker.colorBack))
                .duration(2000)
                .start()
                .onStop {
                    end = System.currentTimeMillis() - start
                    listener1?.invoke(3, end)
                }
        }


    }

/*  fun move_scale(selector: Int, talker: Talker, arr: ArrayList<TextView?>, dur: Long) {

        val linesNum = talker.lines
        if (selector == 0) {
            for (index in 0 until linesNum) {
                arr[index]?.let { item_move_scale(index + 1, it, dur) }
            }
        }

        if (selector == 1) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index == 1) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 2) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 2) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 3) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 3) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 4) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 4) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }
        if (selector == 5) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= 5) {
                        item_scale(index, it, dur)
                    } else {
                        item_move_scale(index, it, dur)

                    }
                }
            }
        }


        if (selector == 6) {
            for (index in 1..linesNum) {
                arr[index - 1]?.let {
                    if (index <= talker.lines - 1) {
                        item_move_scale(index, it, dur)
                    } else {
                        item_scale_swing(index, it, dur)
                    }
                }
            }
        }
    }*/


    private fun ttMove1(
        textView: TextView,
        x0: Float,
        x1: Float,
        y0: Float,
        y1: Float,
        scale0: Float,
        scale1: Float,
        duration: Long
    ) {
        ViewAnimator
            .animate(textView)
            .translationX(x0, x1)
            .translationY(y0, y1)
            .scale(scale0, scale1)
            .duration(duration)
            .start()
    }


    private fun ttScale1(textView: TextView, dur: Long) {
        ViewAnimator.animate(textView).scale(0f, 1f).duration(dur).start()
    }

    fun creatPoints() {
        val leftTop = Point((-wi / 2).toInt(), (-hi).toInt())
    }

    fun getCordinateAndSpine(ind: Int): Array<Float> {
        var x01 = 0f
        var y01 = 0f
        var x02 = 0f
        var y02 = 0f
        var rotate = 360f;

        when (ind) {
            1 -> {
                x01 = -wi / 2
                y01 = -hi
                rotate = 720f * 2
            }
            2 -> {
                x01 = wi / 2
                y01 = -hi
                rotate = -720f * 2
            }
            3 -> {
                x01 = -wi / 2
                y01 = 0f
                rotate = 360f
            }
            4 -> {
                x01 = wi / 2
                y01 = 0f
                rotate = -360f
            }
            5 -> {
                x01 = -wi / 2
                y01 = hi
                rotate = 720f * 2
            }
            6 -> {
                x01 = wi / 2
                y01 = hi
                rotate = -720f * 2
            }
        }
        return arrayOf(x01, y01, x02, y02, rotate)
    }

    fun ttMoveAndRotate(kind: Int, ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)
        if (kind == 30) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .start()
        }
        if (kind == 31) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .thenAnimate(textView)
                .repeatCount(1)
                .swing()
                .start()
        }
        if (kind == 32) {
            ViewAnimator
                .animate(textView)
                .scale(0f, 1f)
                .translationX(arr[0], 0f)
                .translationY(arr[1], 0f)
                .rotation(arr[2])
                .duration(dur)
                .thenAnimate(textView)
                .flipHorizontal()
                .repeatCount(2)
                .start()
        }
    }

    fun scale10(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {
            arr[index]?.let { ttScale1(it, dur) }
        }
    }


    fun scale11(arr: ArrayList<TextView?>, dur: Long) {
        ViewAnimator
            .animate(arr[0])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[1])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[2])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[3])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[4])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[5])
            .scale(0f, 1f)
            .duration(dur)
            .start()
    }


    fun scale12(arr: ArrayList<TextView?>, dur: Long) {
        ViewAnimator
            .animate(arr[0])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[1])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[2])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[3])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[4])
            .scale(0f, 1f)
            .thenAnimate(arr[5])
            .scale(0f, 1f)
            .onStop {
                ViewAnimator
                    .animate(arr[5])
                    .scale(1f, 0f, 1f)
                    .repeatCount(1)
                    .duration(1000)
                    .start()
            }
            .duration(dur)
            .start()
    }

    fun scale13(arr: ArrayList<TextView?>, dur: Long) {
        ViewAnimator
            .animate(arr[0])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[1])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[2])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[3])
            .scale(0f, 1f)
            .duration(dur)
            .thenAnimate(arr[4])
            .scale(0f, 1f)
            .thenAnimate(arr[5])
            .scale(0f, 1f)
            .onStop {
                ViewAnimator
                    .animate(arr[5])
                    .swing()
                    .repeatCount(1)
                    .duration(1000)
                    .start()
            }
            .duration(dur)
            .start()
    }


    fun ttMoveAndRotateAndSwing1(ind: Int, textView: TextView, dur: Long) {
        var arr = getCordinateAndSpine(ind)

        ViewAnimator
            .animate(textView)
            .scale(0f, 1f)
            .translationX(arr[0], 0f)
            .translationY(arr[1], 0f)
            .rotation(arr[2])
            .duration(dur)
            .swing()
            .start()
    }


    /*   fun scale_swing( selector:Int,talker: Talker,arr: ArrayList<TextView?>, dur: Long) {

           if (selector==0) {
               for (index in 0 until arr.size) {
                   arr[index]?.let { item_scale(index + 1, it, dur) }
               }
           }

           if (selector==1) {
               for (index in 0 until arr.size) {
                   arr[index]?.let {
                       if (index > 0) {
                           item_scale(index + 1, it, dur)
                       } else {
                           item_scale_swing(index + 1, it, dur)
                       }
                   }
               }
           }


           if (selector==2) {
               for (index in 0 until arr.size) {
                   arr[index]?.let {
                       if (index == talker.lines-1) {
                           item_scale_swing(index + 1, it, dur)
                       } else {
                           item_scale(index + 1, it, dur)

                       }
                   }
               }
           }
           if (selector==3) {
               for (index in 0 until arr.size) {
                   arr[index]?.let {
                           item_scale_swing(index + 1, it, dur)
                   }
               }
           }
           *//*     if (selector==4) {
                     for (index in 0 until arr.size) {
                         arr[index]?.let {
                             if (index > 3) {
                                 item_move_scale_rotate(index + 1, it, dur)
                             } else {
                                 item_scale(index + 1, it, dur)
                             }
                         }
                     }
                 }
                 if (selector==5) {
                     for (index in 0 until arr.size) {
                         arr[index]?.let {
                             if (index > 4) {
                                 item_move_scale_rotate(index + 1, it, dur)
                             } else {
                                 item_scale(index + 1, it, dur)
                             }
                         }
                     }
                 }*//*

    }*/


    fun moveAndRotate3(kind: Int, arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {
            arr[index]?.let { ttMoveAndRotate(kind, index + 1, it, dur) }
        }
    }


}


/* fun moveAndRotate31(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {

            arr[index]?.let { ttMoveAndRotate(1,index + 1, it, dur) }

        }
    }
    fun moveAndRotate32(arr: ArrayList<TextView?>, dur: Long) {
        for (index in 0..5) {

            arr[index]?.let { ttMoveAndRotate(2,index + 1, it, dur) }

        }*/


/*fun move1(textView1: TextView, textView2: TextView) {
    ttMove1(
        textView1, -wi / 2, 0f, -hi, 0f, 0f, 1f, 2000
    )
    ttMove1(
        textView2, wi / 2, 0f, -hi, 0f, 0f, 1f, 2000
    )
}

fun move1g(textView1: TextView, textView2: TextView) {
    ttMove1(
        textView1,
        wi / 2, 0f, hi, 0f, 0f, 2f, 2000
    )
    ttMove1(
        textView2,
        wi / 2, 0f, hi, 0f, 0f, 2f, 2000
    )
}

fun move1god(textView1: TextView, textView2: TextView, dur: Long) {
    ttMove1(textView1, wi / 2, 0f, hi, 0f, 0f, 1f, dur)
    ttMove1(textView2, -wi / 2, 0f, hi, 0f, 0f, 1f, dur)
}
*/



