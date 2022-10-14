package com.example.xck.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * author: Gubr
 * date: 2018/5/9
 * describe:
 */
fun Fragment.toast(content: String, duration: Int= Toast.LENGTH_SHORT){
    Toast.makeText(activity,content,duration).show()
}